package de.uks.se.learned.util;

import org.sdmlib.models.pattern.PatternObject;
import de.uks.se.learned.JHBoat;
import de.uks.se.learned.util.JHBankPO;
import de.uks.se.learned.util.JHBoatPO;
import de.uks.se.learned.JHBank;

public class JHBoatPO extends PatternObject<JHBoatPO, JHBoat>
{

    public JHBoatSet allMatches()
   {
      this.setDoAllMatches(true);
      
      JHBoatSet matches = new JHBoatSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((JHBoat) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public JHBoatPO(){
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"));
   }

   public JHBoatPO(JHBoat... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }
   public JHBankPO hasOn()
   {
      JHBankPO result = new JHBankPO(new de.uks.se.learned.JHBank[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(JHBoat.PROPERTY_ON, result);
      
      return result;
   }

   public JHBankPO createOn()
   {
      return this.startCreate().hasOn().endCreate();
   }

   public JHBoatPO hasOn(JHBankPO tgt)
   {
      return hasLinkConstraint(tgt, JHBoat.PROPERTY_ON);
   }

   public JHBoatPO createOn(JHBankPO tgt)
   {
      return this.startCreate().hasOn(tgt).endCreate();
   }

   public JHBank getOn()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((JHBoat) this.getCurrentMatch()).getOn();
      }
      return null;
   }

}
