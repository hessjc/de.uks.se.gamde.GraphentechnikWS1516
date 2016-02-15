package de.uks.se.gamde;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

/**
 * Graph object.
 *
 * @author Jan-Christopher Hess (jan-chr.hess@student.uni-kassel.de)
 */
public class JHGraph extends JHNode
{
   public static long maxId = 0;

   private LinkedHashMap<JHNode, String> oldCertificatesMap;
   private LinkedHashMap<JHNode, String> newCertificatesMap;

   private TreeSet<String> oldCertificatesSet = new TreeSet<String>();
   private TreeSet<String> newCertificatesSet;

   private LinkedHashMap<String, JHNodeSet> nodes = new LinkedHashMap<String, JHNodeSet>();

   public LinkedHashMap<String, JHNodeSet> getNodes()
   {
      return nodes;
   }

   public JHNode createNode(String label)
   {
      JHNode node = new JHNode();
      node.setId(maxId + 1);
      maxId++;

      node.setLabel(label);

      JHNodeSet labelNodeSet = nodes.get(label);
      if (labelNodeSet == null)
      {
         labelNodeSet = new JHNodeSet();
         nodes.put(label, labelNodeSet);
      }
      labelNodeSet.add(node);

      return node;
   }

   public JHNode createNode(long id, String label)
   {
      JHNode node = new JHNode();
      node.setId(id);
      maxId = Math.max(maxId, id);

      node.setLabel(label);

      JHNodeSet labelNodeSet = nodes.get(label);
      if (labelNodeSet == null)
      {
         labelNodeSet = new JHNodeSet();
         nodes.put(label, labelNodeSet);
      }
      labelNodeSet.add(node);

      return node;
   }

   public JHNode witheNodes(JHNode newNode)
   {
      maxId = Math.max(maxId, newNode.getId());

      JHNodeSet labelNodeSet = nodes.get(newNode.getLabel());
      if (labelNodeSet == null)
      {
         labelNodeSet = new JHNodeSet();
         nodes.put(newNode.getLabel(), labelNodeSet);
      }
      labelNodeSet.add(newNode);

      return newNode;
   }

   /**
    * @param newNode
    */
   public void addNode(JHNode newNode)
   {
      newNode.setId(maxId++);

      String label = "graph";
      newNode.setLabel(label);

      JHNodeSet labelNodeSet = nodes.get(label);
      if (labelNodeSet == null)
      {
         labelNodeSet = new JHNodeSet();
         nodes.put(label, labelNodeSet);
      }
      labelNodeSet.add(newNode);
   }

   public JHNode getNode(long id)
   {
      for (String label : this.getNodes().keySet())
      {
         for (JHNode n : this.getNodes().get(label))
         {
            if (n.getId() == id)
            {
               return n;
            }
         }
      }
      return null;
   }

   public void createEdge(JHNode src, String label, JHNode tgt)
   {
      src.withEdge(label, tgt);
   }

   public int getEdgesCount()
   {
      int result = 0;
      for (String label : getNodes().keySet())
      {
         for (JHNode jhNode : getNodes().get(label))
         {
            result += jhNode.getNeighbors().values().size();
         }
      }
      return result;
   }

   /**
    * yuml serialization.
    */
   public String serializeGraph()
   {
      String url = "http://yuml.me/diagram/scruffy/class/";
      for (String label : nodes.keySet())
      {
         for (JHNode node : nodes.get(label))
         {
            url = url + "[" + node.getLabel();

            // Attributes
            if (node.getAttributes().size() > 0)
            {
               url = url + "|";
               for (Entry<String, Object> attribute : node.getAttributes().entrySet())
               {
                  url = url + attribute.getKey() + "=" + attribute.getValue();
               }
            }
            url = url + "]";

            // Edges
            if (node.getNeighbors().size() > 0)
            {
               boolean first = true;

               for (Entry<String, JHNodeSet> edges : node.getNeighbors().entrySet())
               {
                  for (JHNode tgtNode : edges.getValue())
                  {
                     if (edges.getKey().startsWith(JHNode._REV) == false)
                     {
                        if (first)
                        {
                           url = url + "-" + edges.getKey() + "->[" + tgtNode.getLabel() + "]";
                        }
                        else
                        {
                           url = url + ",";
                           url = url + "[" + node.getLabel() + "]-" + edges.getKey() + "->["
                                 + tgtNode.getLabel() + "]";
                        }
                        first = false;
                     }
                  }
               }
            }
            url = url + ",";
         }
      }
      url = url.substring(0, url.length() - 1);
      return url;
   }

   /**
    * @return certificate
    * 
    */
   public String getCertificate()
   {
      String graphKey = null;

      // for all nodes compute level 0 certificate
      newCertificatesMap = new LinkedHashMap<JHNode, String>();
      newCertificatesSet = new TreeSet<String>();

      for (JHNodeSet nodeSet : this.getNodes().values())
      {
         for (JHNode node : nodeSet)
         {
            // label plus all attributes
            String certificate = "" + node.getLabel();

            ArrayList<String> attrList = new ArrayList<String>();
            for (String attrName : node.getAttributes().keySet())
            {
               String attrCertificate = attrName + ":" + node.getAttribute(attrName);
               attrList.add(attrCertificate);
            }

            Collections.sort(attrList);
            certificate = certificate + attrList.toString();
            newCertificatesMap.put(node, certificate);
            newCertificatesSet.add(certificate);
         }
      }

      while (newCertificatesSet.size() < newCertificatesMap.keySet().size()
            && newCertificatesSet.size() > oldCertificatesSet.size())
      {
         // next level
         oldCertificatesMap = newCertificatesMap;
         oldCertificatesSet = newCertificatesSet;

         newCertificatesMap = new LinkedHashMap<>();
         newCertificatesSet = new TreeSet<>();

         for (JHNodeSet nodeSet : this.getNodes().values())
         {
            for (JHNode node : nodeSet)
            {
               // my own old certificate plus edges*target
               String certificate = oldCertificatesMap.get(node);

               ArrayList<String> neighborCertificatesList = new ArrayList<String>();
               for (String label : node.getNeighbors().keySet())
               {
                  for (JHNode neighbor : node.getNeighbors().get(label))
                  {
                     String subCertificate = label + "->" + oldCertificatesMap.get(neighbor);
                     neighborCertificatesList.add(subCertificate);
                  }
               }

               Collections.sort(neighborCertificatesList);
               certificate += neighborCertificatesList;

               newCertificatesMap.put(node, certificate);
               newCertificatesSet.add(certificate);
            }
         }
      }

      // sum up.
      graphKey = newCertificatesSet.toString();

      return graphKey;
   }

   /**
    * @param isomorphGraph
    * @return
    */
   public boolean isIsomorph(JHGraph isomorphGraph, JHGraphUtil jhGraphUtil)
   {
      // different count of nodes
      if (this.getNodes().values().size() != isomorphGraph.getNodes().values().size())
      {
         return false;
      }

      // different count of edges
      if (this.getEdgesCount() != isomorphGraph.getEdgesCount())
      {
         return false;
      }

      /* order nodes to compare edges by node. */
      HashMap<JHNode, Integer> graph = getCountByNeighborsMap(this);
      HashMap<JHNode, Integer> comparedGraph = getCountByNeighborsMap(isomorphGraph);

      Map<JHNode, Integer> graphSortedByValue = JHGraphUtil.sortByValue(graph);
      Map<JHNode, Integer> comparedGraphSortedByValue = JHGraphUtil.sortByValue(comparedGraph);

      Iterator<JHNode> graphIterator = graphSortedByValue.keySet().iterator();
      Iterator<JHNode> comparedGraphIterator = comparedGraphSortedByValue.keySet().iterator();
      while (graphIterator.hasNext() && comparedGraphIterator.hasNext())
      {
         JHNode jhNode = graphIterator.next();
         JHNode jhNodeCompared = comparedGraphIterator.next();

         if (graphSortedByValue.get(jhNode) != comparedGraphSortedByValue.get(jhNodeCompared))
         {
            return false;
         }
      }

      /* NEW: take both graphs and compare reachability */
      JHGraphUtil utilForThisGraph = new JHGraphUtil();
      JHGraphUtil utilForOtherGraph = new JHGraphUtil();

      for (JHRule rule : jhGraphUtil.getRuleSet())
      {
         utilForThisGraph.addRule(rule);
         utilForOtherGraph.addRule(rule);
      }

      utilForThisGraph.setStartGraph(this);
      utilForOtherGraph.setStartGraph(isomorphGraph);

      LinkedHashMap<String, JHNodeSet> thisReachableGraphs = utilForThisGraph.computeAllReachableGraphs(false);
      LinkedHashMap<String, JHNodeSet> otherReachableGraphs = utilForOtherGraph.computeAllReachableGraphs(false);

      if (thisReachableGraphs.size() == otherReachableGraphs.size())
      {
         return true;
      }

      return false;
   }

   /**
    * @return map
    */
   private HashMap<JHNode, Integer> getCountByNeighborsMap(JHGraph jhGraph)
   {
      HashMap<JHNode, Integer> graph = new HashMap<JHNode, Integer>();
      for (String nodeLabel : jhGraph.getNodes().keySet())
      {
         for (JHNode jhNode : jhGraph.getNodes().get(nodeLabel))
         {
            graph.put(jhNode, jhNode.getNeighbors().values().size());
         }
      }
      return graph;
   }

   @Override
   public JHGraph clone()
   {
      LinkedHashMap<JHNode, JHNode> cloneMap = new LinkedHashMap<JHNode, JHNode>();

      JHGraph result = new JHGraph();

      for (String label : this.getNodes().keySet())
      {
         for (JHNode origNode : this.getNodes().get(label))
         {
            JHNode newNode = result.createNode(origNode.getLabel());
            newNode.setId(origNode.getId());

            for (String origKey : origNode.getAttributes().keySet())
            {
               newNode.withAttribute(origKey, origNode.getAttribute(origKey));
            }

            cloneMap.put(origNode, newNode);
         }
      }

      for (String label : this.getNodes().keySet())
      {
         for (JHNode origNode : this.getNodes().get(label))
         {
            JHNode srcClone = cloneMap.get(origNode);

            for (String edgeLabel : origNode.getNeighbors().keySet())
            {
               if (edgeLabel.startsWith(JHNode._REV) == false)
               {
                  JHNodeSet neighborSet = origNode.getNeighbors(edgeLabel);
                  for (JHNode origTgt : neighborSet)
                  {
                     JHNode tgtClone = cloneMap.get(origTgt);
                     srcClone.withEdge(edgeLabel, tgtClone);
                  }
               }
            }
         }
      }
      return result;
   }
}