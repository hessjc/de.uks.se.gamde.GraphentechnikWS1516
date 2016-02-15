package de.uks.se.learned.util;

import org.sdmlib.models.pattern.PatternObject;
import de.uks.se.learned.Boat;
import de.uks.se.learned.util.BankPO;
import de.uks.se.learned.util.BoatPO;
import de.uks.se.learned.Bank;

public class BoatPO extends PatternObject<BoatPO, Boat>
{

    public BoatSet allMatches()
   {
      this.setDoAllMatches(true);
      
      BoatSet matches = new BoatSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Boat) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public BoatPO(){
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"));
   }

   public BoatPO(Boat... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }
   public BankPO hasOn()
   {
      BankPO result = new BankPO(new de.uks.se.learned.Bank[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Boat.PROPERTY_ON, result);
      
      return result;
   }

   public BankPO createOn()
   {
      return this.startCreate().hasOn().endCreate();
   }

   public BoatPO hasOn(BankPO tgt)
   {
      return hasLinkConstraint(tgt, Boat.PROPERTY_ON);
   }

   public BoatPO createOn(BankPO tgt)
   {
      return this.startCreate().hasOn(tgt).endCreate();
   }

   public Bank getOn()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Boat) this.getCurrentMatch()).getOn();
      }
      return null;
   }

}
