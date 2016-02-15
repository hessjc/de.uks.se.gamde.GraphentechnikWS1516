package de.uks.se.gamde;

/**
 * SearchOperator object.
 *
 * @author Jan-Christopher Hess (jan-chr.hess@student.uni-kassel.de)
 */
public class JHSearchOp
{
   private JHNode ruleNode;

   private JHNodeSet candidates;

   public JHNode getRuleNode()
   {
      return ruleNode;
   }

   public void setRuleNode(JHNode ruleNode)
   {
      this.ruleNode = ruleNode;
   }

   public JHNodeSet getCandidates()
   {
      return candidates;
   }

   public void setCandidates(JHNodeSet candidates)
   {
      this.candidates = candidates;
   }
}