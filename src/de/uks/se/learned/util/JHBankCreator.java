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
import de.uks.se.learned.JHBank;
import de.uks.se.learned.JHBoat;
import de.uks.se.learned.JHPerson;

public class JHBankCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      JHBank.PROPERTY_SIDE,
      JHBank.PROPERTY__REV_ON,
      JHBank.PROPERTY__REV_AT,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new JHBank();
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

      if (JHBank.PROPERTY_SIDE.equalsIgnoreCase(attribute))
      {
         return ((JHBank) target).getSide();
      }

      if (JHBank.PROPERTY__REV_ON.equalsIgnoreCase(attribute))
      {
         return ((JHBank) target).get_rev_on();
      }

      if (JHBank.PROPERTY__REV_AT.equalsIgnoreCase(attribute))
      {
         return ((JHBank) target).get_rev_at();
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

      if (JHBank.PROPERTY_SIDE.equalsIgnoreCase(attrName))
      {
         ((JHBank) target).withSide((String) value);
         return true;
      }

      if (JHBank.PROPERTY__REV_ON.equalsIgnoreCase(attrName))
      {
         ((JHBank) target).set_rev_on((JHBoat) value);
         return true;
      }

      if (JHBank.PROPERTY__REV_AT.equalsIgnoreCase(attrName))
      {
         ((JHBank) target).with_rev_at((JHPerson) value);
         return true;
      }
      
      if ((JHBank.PROPERTY__REV_AT + JsonIdMap.REMOVE).equalsIgnoreCase(attrName))
      {
         ((JHBank) target).without_rev_at((JHPerson) value);
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
      ((JHBank) entity).removeYou();
   }
}
