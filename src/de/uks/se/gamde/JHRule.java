/**
 * 
 * 
 * @author jan-chr.hess@student.uni-kassel.de
 */
package de.uks.se.gamde;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.logging.Logger;

/**
 * Rule object.
 *
 * @author Jan-Christopher Hess (jan-chr.hess@student.uni-kassel.de)
 */
public class JHRule
{
   private final static Logger LOGGER = Logger.getLogger(JHRule.class.getName());

   private JHGraph leftGraph;

   private LinkedHashSet<JHOperation> operations = new LinkedHashSet<JHOperation>();

   private String ruleName;

   /**
    * @param rule
    */
   public JHRule(String rule)
   {
      this.ruleName = rule;
   }

   /**
    * @return the leftGraph
    */
   public JHGraph getLeftGraph()
   {
      return leftGraph;
   }

   /**
    * @param leftGraph
    * the leftGraph to set
    */
   public void setLeftGraph(JHGraph leftGraph)
   {
      this.leftGraph = leftGraph;
   }

   /**
    * @return the rule
    */
   public String getRuleName()
   {
      return ruleName;
   }

   /**
    * @param g1
    * @return
    */
   public LinkedHashSet<JHMatch> findMatch(JHGraph hostGraph)
   {
      LinkedHashSet<JHMatch> setOfMatches = new LinkedHashSet<JHMatch>();

      JHMatch match = new JHMatch();

      JHSearchOp searchOP = prepareSearchOP(hostGraph, match);

      findMatchForCurrentLeftSideNode(searchOP, hostGraph, setOfMatches, match);

      return setOfMatches;
   }

   private JHSearchOp prepareSearchOP(JHGraph hostGraph, LinkedHashMap<JHNode, JHNode> match)
   {
      JHSearchOp result = null;

      for (String label : this.getLeftGraph().getNodes().keySet())
      {
         for (JHNode ruleNode : this.getLeftGraph().getNodes().get(label))
         {
            if (match.get(ruleNode) != null)
            {
               // is there an edge to an not yet mapped node
               for (String edgeLabel : ruleNode.getNeighbors().keySet())
               {
                  for (JHNode otherRuleNode : ruleNode.getNeighbors().get(edgeLabel))
                  {
                     if (match.get(otherRuleNode) == null)
                     {
                        JHNode hostNode = match.get(ruleNode);
                        JHNodeSet jhNodeSet = hostNode.getNeighbors().get(edgeLabel);

                        if (jhNodeSet == null)
                        {
                           jhNodeSet = new JHNodeSet();
                        }

                        if (result == null || result.getCandidates().size() > jhNodeSet.size())
                        {
                           if (result == null)
                           {
                              result = new JHSearchOp();
                           }

                           result.setRuleNode(otherRuleNode);
                           result.setCandidates(jhNodeSet);
                        }
                     }
                  }
               }
               continue;
            }
            // how many candidates
            JHNodeSet jhNodeSet = hostGraph.getNodes().get(label);

            if (result == null || result.getCandidates().size() > jhNodeSet.size())
            {
               if (result == null)
               {
                  result = new JHSearchOp();
               }

               result.setRuleNode(ruleNode);
               result.setCandidates(jhNodeSet);
            }
         }
      }

      return result;
   }

   /**
    * @param hostGraph
    * @param leftNodes
    * @param currentNodeIndex
    * @param hostGraph
    * @param setOfMatches
    * @param match
    * @return
    */
   private boolean findMatchForCurrentLeftSideNode(JHSearchOp searchOp, JHGraph hostGraph,
         LinkedHashSet<JHMatch> setOfMatches, LinkedHashMap<JHNode, JHNode> match)
   {
      // no more left side nodes
      if (searchOp == null)
      {
         JHMatch completeMatch = (JHMatch)match.clone();
         setOfMatches.add(completeMatch);

         // we are done
         return false;
      }

      JHNode currentLeftNode = searchOp.getRuleNode();

      for (JHNode hostNode : hostGraph.getNodes().get(currentLeftNode.getLabel()))
      {
         // check attributes
         boolean allAttributesMatch = true;
         for (String attrName : currentLeftNode.getAttributes().keySet())
         {
            if (currentLeftNode.getAttribute(attrName).equals(hostNode.getAttribute(attrName)) == false)
            {
               allAttributesMatch = false;
               break;
            }
         }

         if (allAttributesMatch == false)
         {
            continue;
         }

         // check edges
         boolean allEdgesMatch = true;
         for (String edgeLabel : currentLeftNode.getNeighbors().keySet())
         {
            for (JHNode leftTgtNode : currentLeftNode.getNeighbors(edgeLabel))
            {
               JHNode hostTgtNode = match.get(leftTgtNode);
               if (match.get(leftTgtNode) == null)
               {
                  // to early
                  continue;
               }

               // edge exists in host graph
               JHNodeSet neighbors = hostNode.getNeighbors(edgeLabel);
               if (neighbors == null || neighbors.contains(hostTgtNode) == false)
               {
                  allEdgesMatch = false;
                  break;
               }
            }

            if (allEdgesMatch == false)
            {
               break;
            }
         }

         if (allEdgesMatch == false)
         {
            continue;
         }

         // seems to be a good candidate
         match.put(currentLeftNode, hostNode);
         LOGGER.info(hostNode + " seems to be a good candidate for " + currentLeftNode);

         JHSearchOp nextSearchOp = prepareSearchOP(hostGraph, match);
         boolean success = findMatchForCurrentLeftSideNode(nextSearchOp, hostGraph, setOfMatches, match);

         if (success)
         {
            // all nodes are matched
            return true;
         }
         else
         {
            match.remove(currentLeftNode);
            LOGGER.info(hostNode + " is no good candidate for " + currentLeftNode);
         }
      }
      return false;
   }

   /**
    * @param opCode
    * @param startNode
    * @param label
    * @param endNode
    */
   public void addOperation(String opCode, JHNode startNode, String label, JHNode endNode)
   {
      JHOperation jhOperation = new JHOperation(opCode, startNode, label, endNode);

      operations.add(jhOperation);
   }

   /**
    * @param clone
    * @param idMap
    * @param match
    */
   public void executeOperations(JHGraph clone, LinkedHashMap<Long, JHNode> idMap, JHMatch match)
   {
      for (JHOperation op : operations)
      {
         if (JHOperation.WITHOUT_EDGE.equals(op.getOpCode()))
         {
            // find host graph nodes
            JHNode startNodePO = op.getStartNode();
            JHNode endNodePO = op.getEndNode();

            JHNode originalStartNode = match.get(startNodePO);
            JHNode originalEndNode = match.get(endNodePO);

            long startNodeId = originalStartNode.getId();
            long endNodeId = originalEndNode.getId();

            JHNode cloneStartNode = idMap.get(startNodeId);
            JHNode cloneEndNode = idMap.get(endNodeId);

            cloneStartNode.withoutEdge(op.getLabel(), cloneEndNode);
         }
         else if (JHOperation.WITH_EDGE.equals(op.getOpCode()))
         {
            // find host graph nodes
            JHNode startNodePO = op.getStartNode();
            JHNode endNodePO = op.getEndNode();

            JHNode originalStartNode = match.get(startNodePO);
            JHNode originalEndNode = match.get(endNodePO);

            long startNodeId = originalStartNode.getId();
            long endNodeId = originalEndNode.getId();

            JHNode cloneStartNode = idMap.get(startNodeId);
            JHNode cloneEndNode = idMap.get(endNodeId);

            cloneStartNode.withEdge(op.getLabel(), cloneEndNode);
         }
      }
   }
}
