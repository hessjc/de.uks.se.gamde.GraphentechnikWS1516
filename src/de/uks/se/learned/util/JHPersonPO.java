package de.uks.se.learned.util;

import org.sdmlib.models.pattern.PatternObject;
import de.uks.se.learned.JHPerson;
import org.sdmlib.models.pattern.AttributeConstraint;
import de.uks.se.learned.util.JHBankPO;
import de.uks.se.learned.util.JHPersonPO;
import de.uks.se.learned.JHBank;

public class JHPersonPO extends PatternObject<JHPersonPO, JHPerson>
{

    public JHPersonSet allMatches()
   {
      this.setDoAllMatches(true);
      
      JHPersonSet matches = new JHPersonSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((JHPerson) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public JHPersonPO(){
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"));
   }

   public JHPersonPO(JHPerson... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }
   public JHPersonPO hasType(String value)
   {
      new AttributeConstraint()
      .withAttrName(JHPerson.PROPERTY_TYPE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.hasAttr();
      
      return this;
   }
   
   public JHPersonPO hasType(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(JHPerson.PROPERTY_TYPE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.hasAttr();
      
      return this;
   }
   
   public JHPersonPO createType(String value)
   {
      this.startCreate().hasType(value).endCreate();
      return this;
   }
   
   public String getType()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((JHPerson) getCurrentMatch()).getType();
      }
      return null;
   }
   
   public JHPersonPO withType(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((JHPerson) getCurrentMatch()).setType(value);
      }
      return this;
   }
   
   public JHBankPO hasAt()
   {
      JHBankPO result = new JHBankPO(new de.uks.se.learned.JHBank[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(JHPerson.PROPERTY_AT, result);
      
      return result;
   }

   public JHBankPO createAt()
   {
      return this.startCreate().hasAt().endCreate();
   }

   public JHPersonPO hasAt(JHBankPO tgt)
   {
      return hasLinkConstraint(tgt, JHPerson.PROPERTY_AT);
   }

   public JHPersonPO createAt(JHBankPO tgt)
   {
      return this.startCreate().hasAt(tgt).endCreate();
   }

   public JHBank getAt()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((JHPerson) this.getCurrentMatch()).getAt();
      }
      return null;
   }

}
