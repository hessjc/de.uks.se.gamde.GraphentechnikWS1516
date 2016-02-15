package de.uks.se.gamde;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * NodeSet object (representing HashSet).
 *
 * @author Jan-Christopher Hess (jan-chr.hess@student.uni-kassel.de)
 */
@SuppressWarnings("serial")
public class JHNodeSet extends LinkedHashSet<JHNode>
{
   public JHNode first()
   {
      Iterator<JHNode> iterator = this.iterator();

      if (iterator.hasNext())
      {
         return iterator.next();
      }
      return null;
   }

   /**
    * @return result
    * 
    */
   public JHNodeSet getNeighbors(String edgeLabel)
   {
      JHNodeSet result = new JHNodeSet();
      for (JHNode jhNode : this)
      {
         JHNodeSet neighbors = jhNode.getNeighbors(edgeLabel);
         result.addAll(neighbors);
      }
      return result;
   }

   /**
    * @return result
    * 
    */
   public JHNodeSet getNeighborsForward()
   {
      JHNodeSet result = new JHNodeSet();
      for (JHNode jhNode : this)
      {
         for (String neighborLabel : jhNode.getNeighbors().keySet())
         {
            if (neighborLabel.startsWith(JHNode._REV) == true) continue;
            result.addAll(jhNode.getNeighbors(neighborLabel));
         }
      }
      return result;
   }

   /**
    * @return result
    * 
    */
   public JHNodeSet getNeighborsTransitive(String edgeLabel)
   {
      JHNodeSet result = new JHNodeSet();
      for (JHNode jhNode : this)
      {
         JHNodeSet neighbors = jhNode.getNeighborsTransitive(edgeLabel);
         result.addAll(neighbors);
      }
      return result;
   }

   /**
    * @return result
    * 
    */
   public JHNodeSet getNeighborsForwardTransitive()
   {
      JHNodeSet result = new JHNodeSet();
      for (JHNode jhNode : this)
      {
         for (String neighborLabel : jhNode.getNeighbors().keySet())
         {
            // only foward transitive
            if (neighborLabel.startsWith(JHNode._REV) == true) continue;
            JHNodeSet neighbors = jhNode.getNeighborsTransitive(neighborLabel);
            result.addAll(neighbors);
         }
      }
      return result;
   }

   /**
    * @param graphSet
    * @return
    */
   public JHNodeSet union(JHNodeSet graphSet)
   {
      JHNodeSet result = new JHNodeSet();
      result.addAll(this);
      result.addAll(graphSet);
      return result;
   }

   /**
    * @param graphSet
    * @return
    */
   public JHNodeSet intersection(JHNodeSet graphSet)
   {
      JHNodeSet result = new JHNodeSet();
      for (JHNode jhNode : this)
      {
         if (graphSet.contains(jhNode)) result.add(jhNode);
      }
      return result;
   }

   /**
    * @param graphSet
    * @return
    */
   public JHNodeSet minus(JHNodeSet graphSet)
   {
      JHNodeSet result = new JHNodeSet();
      result.addAll(this);
      for (JHNode jhNode : graphSet)
      {
         if (graphSet.contains(jhNode)) result.remove(jhNode);
      }
      return result;
   }
}