package de.uks.se.learned.util;

import org.sdmlib.models.pattern.PatternObject;
import de.uks.se.learned.Person;
import org.sdmlib.models.pattern.AttributeConstraint;
import de.uks.se.learned.util.BankPO;
import de.uks.se.learned.util.PersonPO;
import de.uks.se.learned.Bank;

public class PersonPO extends PatternObject<PersonPO, Person>
{

    public PersonSet allMatches()
   {
      this.setDoAllMatches(true);
      
      PersonSet matches = new PersonSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Person) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public PersonPO(){
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"));
   }

   public PersonPO(Person... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(de.uks.se.learned.util.CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }
   public PersonPO hasType(String value)
   {
      new AttributeConstraint()
      .withAttrName(Person.PROPERTY_TYPE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.hasAttr();
      
      return this;
   }
   
   public PersonPO hasType(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Person.PROPERTY_TYPE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.hasAttr();
      
      return this;
   }
   
   public PersonPO createType(String value)
   {
      this.startCreate().hasType(value).endCreate();
      return this;
   }
   
   public String getType()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Person) getCurrentMatch()).getType();
      }
      return null;
   }
   
   public PersonPO withType(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Person) getCurrentMatch()).setType(value);
      }
      return this;
   }
   
   public BankPO hasAt()
   {
      BankPO result = new BankPO(new de.uks.se.learned.Bank[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Person.PROPERTY_AT, result);
      
      return result;
   }

   public BankPO createAt()
   {
      return this.startCreate().hasAt().endCreate();
   }

   public PersonPO hasAt(BankPO tgt)
   {
      return hasLinkConstraint(tgt, Person.PROPERTY_AT);
   }

   public PersonPO createAt(BankPO tgt)
   {
      return this.startCreate().hasAt(tgt).endCreate();
   }

   public Bank getAt()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Person) this.getCurrentMatch()).getAt();
      }
      return null;
   }

}
