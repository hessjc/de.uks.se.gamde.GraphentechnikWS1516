package de.uks.se.learned.util;

import org.sdmlib.models.pattern.PatternObject;
import de.uks.se.learned.JHBank;
import org.sdmlib.models.pattern.AttributeConstraint;
import de.uks.se.learned.util.JHBoatPO;
import de.uks.se.learned.util.JHBankPO;
import de.uks.se.learned.JHBoat;
import de.uks.se.learned.util.JHPersonPO;
import de.uks.se.learned.util.JHPersonSet;

public class JHBankPO extends PatternObject<JHBankPO, JHBank>
{

    public JHBankSet allMatches()
   {
      this.setDoAllMatches(true);
      
      JHBankSet matches = new JHBankSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((JHBank) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public JHBankPO(){
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"));
   }

   public JHBankPO(JHBank... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }
   public JHBankPO hasSide(String value)
   {
      new AttributeConstraint()
      .withAttrName(JHBank.PROPERTY_SIDE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.hasAttr();
      
      return this;
   }
   
   public JHBankPO hasSide(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(JHBank.PROPERTY_SIDE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.hasAttr();
      
      return this;
   }
   
   public JHBankPO createSide(String value)
   {
      this.startCreate().hasSide(value).endCreate();
      return this;
   }
   
   public String getSide()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((JHBank) getCurrentMatch()).getSide();
      }
      return null;
   }
   
   public JHBankPO withSide(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((JHBank) getCurrentMatch()).setSide(value);
      }
      return this;
   }
   
   public JHBoatPO has_rev_on()
   {
      JHBoatPO result = new JHBoatPO(new de.uks.se.learned.JHBoat[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(JHBank.PROPERTY__REV_ON, result);
      
      return result;
   }

   public JHBoatPO create_rev_on()
   {
      return this.startCreate().has_rev_on().endCreate();
   }

   public JHBankPO has_rev_on(JHBoatPO tgt)
   {
      return hasLinkConstraint(tgt, JHBank.PROPERTY__REV_ON);
   }

   public JHBankPO create_rev_on(JHBoatPO tgt)
   {
      return this.startCreate().has_rev_on(tgt).endCreate();
   }

   public JHBoat get_rev_on()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((JHBank) this.getCurrentMatch()).get_rev_on();
      }
      return null;
   }

   public JHPersonPO has_rev_at()
   {
      JHPersonPO result = new JHPersonPO(new de.uks.se.learned.JHPerson[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(JHBank.PROPERTY__REV_AT, result);
      
      return result;
   }

   public JHPersonPO create_rev_at()
   {
      return this.startCreate().has_rev_at().endCreate();
   }

   public JHBankPO has_rev_at(JHPersonPO tgt)
   {
      return hasLinkConstraint(tgt, JHBank.PROPERTY__REV_AT);
   }

   public JHBankPO create_rev_at(JHPersonPO tgt)
   {
      return this.startCreate().has_rev_at(tgt).endCreate();
   }

   public JHPersonSet get_rev_at()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((JHBank) this.getCurrentMatch()).get_rev_at();
      }
      return null;
   }

}
