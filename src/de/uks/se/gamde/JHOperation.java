/**
 * @file file description.
 *
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.gamde;

/**
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 *
 */
public class JHOperation
{
   private String opCode;
   private JHNode startNode;
   private String label;
   private JHNode endNode;
   /**
    * 
    */
   public static final String WITH_EDGE = "withEdge";
   /**
    * 
    */
   public static final String WITHOUT_EDGE = "withoutEdge";

   /**
    * @param opCode
    * @param startNode
    * @param label
    * @param endNode
    */
   public JHOperation(String opCode, JHNode startNode, String label, JHNode endNode)
   {
      this.opCode = opCode;
      this.startNode = startNode;
      this.label = label;
      this.endNode = endNode;
   }

   public String getOpCode()
   {
      return opCode;
   }

   public void setOpCode(String opCode)
   {
      this.opCode = opCode;
   }

   public JHNode getStartNode()
   {
      return startNode;
   }

   public void setStartNode(JHNode startNode)
   {
      this.startNode = startNode;
   }

   public String getLabel()
   {
      return label;
   }

   public void setLabel(String label)
   {
      this.label = label;
   }

   public JHNode getEndNode()
   {
      return endNode;
   }

   public void setEndNode(JHNode endNode)
   {
      this.endNode = endNode;
   }
}
