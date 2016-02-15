package de.uks.se.gamde;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JHGraphUtil
{
   private static final String TARGET_NODE_LABEL = "targetNodeLabel";
   private static final String TARGET = "target";
   private static final String EDGE_LABEL = "edgeLabel";
   private static final String ATTR_VALUE = "attrValue";
   private static final String ATTR_NAME = "attrName";
   private static final String ID = "id";
   private static final String LABEL = "label";

   public JsonArray toJson(JHGraph g1)
   {
      JsonArray jsonArray = new JsonArray();

      for (String nodeLabel : g1.getNodes().keySet())
      {
         for (JHNode node : g1.getNodes().get(nodeLabel))
         {
            JsonObject jsonObject = new JsonObject();
            jsonArray.add(jsonObject);

            jsonObject.addProperty(ID, node.getId());
            jsonObject.addProperty(LABEL, node.getLabel());

            for (String attributeLabel : node.getAttributes().keySet())
            {
               jsonObject = new JsonObject();
               jsonArray.add(jsonObject);

               jsonObject.addProperty(ID, node.getId());
               jsonObject.addProperty(ATTR_NAME, attributeLabel);
               jsonObject.addProperty(ATTR_VALUE, node.getAttribute(attributeLabel).toString());
            }

            for (String edgeLabel : node.getNeighbors().keySet())
            {
               if (edgeLabel.startsWith(JHNode._REV) == false)
               {
                  for (JHNode other : node.getNeighbors(edgeLabel))
                  {
                     jsonObject = new JsonObject();
                     jsonArray.add(jsonObject);

                     jsonObject.addProperty(ID, node.getId());
                     jsonObject.addProperty(EDGE_LABEL, edgeLabel);
                     jsonObject.addProperty(TARGET, other.getId());
                     jsonObject.addProperty(TARGET_NODE_LABEL, other.getLabel());
                  }
               }
            }
         }
      }
      return jsonArray;
   }

   public String toString(JsonArray json)
   {
      StringBuilder buf = new StringBuilder();

      for (JsonElement jsonElement : json)
      {
         String string = jsonElement.toString();
         buf.append(string).append("\n");
      }
      return buf.toString();
   }

   public JHGraph fromString(String result)
   {
      Gson gson = new Gson();

      JHGraph jhGraph = new JHGraph();
      idMap = new LinkedHashMap<Long, JHNode>();

      String[] split = result.split("\n");

      for (String line : split)
      {
         JsonObject fromJson = gson.fromJson(line, JsonObject.class);
         JsonElement idElement = fromJson.get(ID);

         // node
         JsonElement jsonElement = fromJson.get(LABEL);

         if (jsonElement != null)
         {
            if (idMap.get(idElement.getAsLong()) == null)
            {
               JHNode jhNode = jhGraph.createNode(idElement.getAsLong(), jsonElement.getAsString());
               idMap.put(jhNode.getId(), jhNode);
            }
         }

         // attributes
         jsonElement = fromJson.get(ATTR_NAME);

         if (jsonElement != null)
         {
            JHNode jhNode = idMap.get(idElement.getAsLong());
            String attrName = jsonElement.getAsString();
            Object value = fromJson.get(ATTR_VALUE).getAsString();

            jhNode.withAttribute(attrName, value);
         }

         // edges
         jsonElement = fromJson.get(EDGE_LABEL);

         if (jsonElement != null)
         {
            JHNode jhNode = idMap.get(idElement.getAsLong());
            long targetId = fromJson.get(TARGET).getAsLong();

            JHNode targetNode = idMap.get(targetId);
            if (targetNode == null)
            {
               targetNode = jhGraph.createNode(targetId, fromJson.get(TARGET_NODE_LABEL).getAsString());
               idMap.put(targetNode.getId(), targetNode);
            }

            jhNode.withEdge(jsonElement.getAsString(), targetNode);
         }
      }
      return jhGraph;
   }

   private LinkedHashSet<JHRule> ruleSet;

   /**
    * @param jhRule
    */
   public void addRule(JHRule jhRule)
   {
      if (ruleSet == null)
      {
         ruleSet = new LinkedHashSet<JHRule>();
      }
      ruleSet.add(jhRule);
   }

   public LinkedHashSet<JHRule> getRuleSet()
   {
      return ruleSet;
   }

   private JHGraph reachabilityGraph = null;
   private LinkedHashSet<JHGraph> todoGraphs = null;
   private LinkedHashMap<Long, JHNode> idMap;
   private LinkedHashMap<String, JHNodeSet> graphMap = new LinkedHashMap<String, JHNodeSet>();

   /**
    * @param startGraph
    */
   public void setStartGraph(JHGraph startGraph)
   {
      if (reachabilityGraph == null)
      {
         reachabilityGraph = new JHGraph();
         todoGraphs = new LinkedHashSet<JHGraph>();
      }

      reachabilityGraph.addNode(startGraph);
      todoGraphs.add(startGraph);

      String graphKey = startGraph.getCertificate();
      JHNodeSet jhNodeSet = new JHNodeSet();
      jhNodeSet.add(startGraph);
      graphMap.put(graphKey, jhNodeSet);
   }

   /**
    * 
    */
   public LinkedHashMap<String, JHNodeSet> computeAllReachableGraphs(boolean checkIsmorphism)
   {
      while (todoGraphs.isEmpty() == false)
      {
         JHGraph currentGraph = todoGraphs.iterator().next();

         todoGraphs.remove(currentGraph);

         for (JHRule rule : ruleSet)
         {
            LinkedHashSet<JHMatch> setOfMatches = rule.findMatch(currentGraph);

            for (JHMatch match : setOfMatches)
            {
               // apply rule on match get followUpGraph
               JsonArray json = toJson(currentGraph);
               String originGraph = toString(json);
               JHGraph clone = fromString(originGraph);

               // execute rule
               rule.executeOperations(clone, idMap, match);

               // test whether the clone is really a new graph
               String cloneKey = clone.getCertificate();
               JHNodeSet oldGraph = graphMap.get(cloneKey);

               if (oldGraph != null)
               {
                  // isomorphism tests
                  if (checkIsmorphism == true)
                  {
                     if (currentGraph.isIsomorph(clone, this))
                     {
                        // yes they are
                        currentGraph.withEdge(rule.getRuleName(), oldGraph.first());
                     }
                  }
               }
               else
               {
                  // add to reachabilityGraph
                  reachabilityGraph.addNode(clone);

                  todoGraphs.add(clone);

                  currentGraph.withEdge(rule.getRuleName(), clone);

                  JHNodeSet cloneSet = new JHNodeSet();
                  cloneSet.add(clone);
                  graphMap.put(cloneKey, cloneSet);
               }
            }
         }
      }
      return graphMap;
   }

   public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map)
   {
      Map<K, V> result = new LinkedHashMap<>();
      Stream<Entry<K, V>> st = map.entrySet().stream();

      st.sorted(Comparator.comparing(e -> e.getValue()))
            .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));

      return result;
   }
}