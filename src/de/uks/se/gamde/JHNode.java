package de.uks.se.gamde;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * Node object.
 *
 * @author Jan-Christopher Hess (jan-chr.hess@student.uni-kassel.de)
 */
public class JHNode
{
   public static final String _REV = "_rev_";

   private long id;

   private String label;

   private HashMap<String, JHNodeSet> neighbors = new LinkedHashMap<String, JHNodeSet>();

   private HashMap<String, Object> attributes = new LinkedHashMap<String, Object>();

   public void setId(long id)
   {
      this.id = id;
   }

   public long getId()
   {
      return id;
   }

   public void setLabel(String label)
   {
      this.label = label;
   }

   public String getLabel()
   {
      return label;
   }

   public HashMap<String, JHNodeSet> getNeighbors()
   {
      return neighbors;
   }

   public JHNodeSet getNeighbors(String edgeLabel)
   {
      if (neighbors != null)
      {
         JHNodeSet jhNodeSet = neighbors.get(edgeLabel);
         if (jhNodeSet != null)
         {
            return jhNodeSet;
         }
      }
      return new JHNodeSet();
   }

   /**
    * @param edgeLabel
    * @return
    */
   public JHNodeSet getNeighborsTransitive(String edgeLabel)
   {
      JHNodeSet done = new JHNodeSet();
      JHNodeSet todo = new JHNodeSet();

      todo.add(this);

      while (todo.size() > 0)
      {
         JHNode currentNode = todo.first();

         todo.remove(currentNode);
         done.add(currentNode);

         JHNodeSet newNeighbors = currentNode.getNeighbors(edgeLabel);
         if (newNeighbors != null)
         {
            for (JHNode jhNode : newNeighbors)
            {
               if (done.contains(jhNode) == false)
               {
                  // this is a new node
                  todo.add(jhNode);
               }
            }
         }
      }
      return done;
   }

   public HashMap<String, Object> getAttributes()
   {
      return attributes;
   }

   public Object getAttribute(String key)
   {
      return attributes.get(key);
   }

   public JHNode withEdge(String label, JHNode tgt)
   {
      JHNodeSet jhNodeSet = neighbors.get(label);

      if (jhNodeSet == null)
      {
         jhNodeSet = new JHNodeSet();
         neighbors.put(label, jhNodeSet);
      }

      boolean add = jhNodeSet.add(tgt);

      if (add && label.startsWith(_REV) == false)
      {
         tgt.withEdge(_REV + label, this);
      }

      return this;
   }

   public JHNode withAttribute(String attrName, Object value)
   {
      attributes.put(attrName, value);
      return this;
   }

   public JHNode withoutEdge(String label, JHNode tgt)
   {
      if (neighbors == null)
      {
         return this;
      }
      JHNodeSet neighborSet = neighbors.get(label);

      if (neighborSet == null)
      {
         return this;
      }

      neighborSet.remove(tgt);
      if (label.startsWith(_REV) == false)
      {
         tgt.withoutEdge(_REV + label, this);
      }
      return this;
   }

   /**
    * @return result
    */
   public String printAttributes()
   {
      String result = "with Attributes: ";
      for (Entry<String, Object> attributes : this.getAttributes().entrySet())
      {
         result += attributes.getKey() + ":" + attributes.getValue().toString() + " \\ ";
      }
      return result;
   }
}