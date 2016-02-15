/*
   Copyright (c) 2016 Jan
   
   Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
   and associated documentation files (the "Software"), to deal in the Software without restriction, 
   including without limitation the rights to use, copy, modify, merge, publish, distribute, 
   sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
   furnished to do so, subject to the following conditions: 
   
   The above copyright notice and this permission notice shall be included in all copies or 
   substantial portions of the Software. 
   
   The Software shall be used for Good, not Evil. 
   
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
   BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
   DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
   
package de.uks.se.learned.util;

import org.sdmlib.serialization.EntityFactory;
import de.uniks.networkparser.json.JsonIdMap;
import de.uks.se.learned.Bank;
import de.uks.se.learned.Boat;
import de.uks.se.learned.Person;

public class BankCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      Bank.PROPERTY_SIDE,
      Bank.PROPERTY__REV_ON,
      Bank.PROPERTY__REV_AT,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Bank();
   }
   
   @Override
   public Object getValue(Object target, String attrName)
   {
      int pos = attrName.indexOf('.');
      String attribute = attrName;
      
      if (pos > 0)
      {
         attribute = attrName.substring(0, pos);
      }

      if (Bank.PROPERTY_SIDE.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).getSide();
      }

      if (Bank.PROPERTY__REV_ON.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).get_rev_on();
      }

      if (Bank.PROPERTY__REV_AT.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).get_rev_at();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (JsonIdMap.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (Bank.PROPERTY_SIDE.equalsIgnoreCase(attrName))
      {
         ((Bank) target).withSide((String) value);
         return true;
      }

      if (Bank.PROPERTY__REV_ON.equalsIgnoreCase(attrName))
      {
         ((Bank) target).set_rev_on((Boat) value);
         return true;
      }

      if (Bank.PROPERTY__REV_AT.equalsIgnoreCase(attrName))
      {
         ((Bank) target).with_rev_at((Person) value);
         return true;
      }
      
      if ((Bank.PROPERTY__REV_AT + JsonIdMap.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Bank) target).without_rev_at((Person) value);
         return true;
      }
      
      return false;
   }
   public static JsonIdMap createIdMap(String sessionID)
   {
      return de.uks.se.learned.util.CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((Bank) entity).removeYou();
   }
}
