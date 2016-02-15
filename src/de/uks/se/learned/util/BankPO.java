package de.uks.se.learned.util;

import org.sdmlib.models.pattern.PatternObject;
import de.uks.se.learned.Bank;
import org.sdmlib.models.pattern.AttributeConstraint;
import de.uks.se.learned.util.BoatPO;
import de.uks.se.learned.util.BankPO;
import de.uks.se.learned.Boat;
import de.uks.se.learned.util.PersonPO;
import de.uks.se.learned.util.PersonSet;

public class BankPO extends PatternObject<BankPO, Bank>
{

    public BankSet allMatches()
   {
      this.setDoAllMatches(true);
      
      BankSet matches = new BankSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Bank) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public BankPO(){
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"));
   }

   public BankPO(Bank... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }
   public BankPO hasSide(String value)
   {
      new AttributeConstraint()
      .withAttrName(Bank.PROPERTY_SIDE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.hasAttr();
      
      return this;
   }
   
   public BankPO hasSide(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Bank.PROPERTY_SIDE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.hasAttr();
      
      return this;
   }
   
   public BankPO createSide(String value)
   {
      this.startCreate().hasSide(value).endCreate();
      return this;
   }
   
   public String getSide()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).getSide();
      }
      return null;
   }
   
   public BankPO withSide(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Bank) getCurrentMatch()).setSide(value);
      }
      return this;
   }
   
   public BoatPO has_rev_on()
   {
      BoatPO result = new BoatPO(new de.uks.se.learned.Boat[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Bank.PROPERTY__REV_ON, result);
      
      return result;
   }

   public BoatPO create_rev_on()
   {
      return this.startCreate().has_rev_on().endCreate();
   }

   public BankPO has_rev_on(BoatPO tgt)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY__REV_ON);
   }

   public BankPO create_rev_on(BoatPO tgt)
   {
      return this.startCreate().has_rev_on(tgt).endCreate();
   }

   public Boat get_rev_on()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) this.getCurrentMatch()).get_rev_on();
      }
      return null;
   }

   public PersonPO has_rev_at()
   {
      PersonPO result = new PersonPO(new de.uks.se.learned.Person[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Bank.PROPERTY__REV_AT, result);
      
      return result;
   }

   public PersonPO create_rev_at()
   {
      return this.startCreate().has_rev_at().endCreate();
   }

   public BankPO has_rev_at(PersonPO tgt)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY__REV_AT);
   }

   public BankPO create_rev_at(PersonPO tgt)
   {
      return this.startCreate().has_rev_at(tgt).endCreate();
   }

   public PersonSet get_rev_at()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) this.getCurrentMatch()).get_rev_at();
      }
      return null;
   }

}
