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

import org.sdmlib.models.modelsets.SDMSet;
import de.uks.se.learned.JHPerson;
import java.util.Collection;
import org.sdmlib.models.modelsets.StringList;
import org.sdmlib.models.modelsets.ObjectSet;
import de.uks.se.learned.util.JHBankSet;
import de.uks.se.learned.JHBank;

public class JHPersonSet extends SDMSet<JHPerson>
{

   public static final JHPersonSet EMPTY_SET = new JHPersonSet().withReadOnly(true);


   public JHPersonPO hasJHPersonPO()
   {
      return new JHPersonPO(this.toArray(new JHPerson[this.size()]));
   }


   public String getEntryType()
   {
      return "de.uks.se.learned.JHPerson";
   }


   @SuppressWarnings("unchecked")
   public JHPersonSet with(Object value)
   {
      if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<JHPerson>)value);
      }
      else if (value != null)
      {
         this.add((JHPerson) value);
      }
      
      return this;
   }
   
   public JHPersonSet without(JHPerson value)
   {
      this.remove(value);
      return this;
   }

   public StringList getType()
   {
      StringList result = new StringList();
      
      for (JHPerson obj : this)
      {
         result.add(obj.getType());
      }
      
      return result;
   }

   public JHPersonSet hasType(String value)
   {
      JHPersonSet result = new JHPersonSet();
      
      for (JHPerson obj : this)
      {
         if (value.equals(obj.getType()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }

   public JHPersonSet hasType(String lower, String upper)
   {
      JHPersonSet result = new JHPersonSet();
      
      for (JHPerson obj : this)
      {
         if (lower.compareTo(obj.getType()) <= 0 && obj.getType().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }

   public JHPersonSet withType(String value)
   {
      for (JHPerson obj : this)
      {
         obj.setType(value);
      }
      
      return this;
   }

   public JHBankSet getAt()
   {
      JHBankSet result = new JHBankSet();
      
      for (JHPerson obj : this)
      {
         result.add(obj.getAt());
      }
      
      return result;
   }

   public JHPersonSet hasAt(Object value)
   {
      ObjectSet neighbors = new ObjectSet();

      if (value instanceof Collection)
      {
         neighbors.addAll((Collection<?>) value);
      }
      else
      {
         neighbors.add(value);
      }
      
      JHPersonSet answer = new JHPersonSet();
      
      for (JHPerson obj : this)
      {
         if (neighbors.contains(obj.getAt()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   public JHPersonSet withAt(JHBank value)
   {
      for (JHPerson obj : this)
      {
         obj.withAt(value);
      }
      
      return this;
   }

}
