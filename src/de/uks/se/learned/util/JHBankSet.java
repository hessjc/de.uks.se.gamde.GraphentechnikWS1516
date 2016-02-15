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
import de.uks.se.learned.JHBank;
import java.util.Collection;
import org.sdmlib.models.modelsets.StringList;
import org.sdmlib.models.modelsets.ObjectSet;
import de.uks.se.learned.util.JHBoatSet;
import de.uks.se.learned.JHBoat;
import java.util.Collections;
import de.uks.se.learned.util.JHPersonSet;
import de.uks.se.learned.JHPerson;

public class JHBankSet extends SDMSet<JHBank>
{

   public static final JHBankSet EMPTY_SET = new JHBankSet().withReadOnly(true);


   public JHBankPO hasJHBankPO()
   {
      return new JHBankPO(this.toArray(new JHBank[this.size()]));
   }


   public String getEntryType()
   {
      return "de.uks.se.learned.JHBank";
   }


   @SuppressWarnings("unchecked")
   public JHBankSet with(Object value)
   {
      if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<JHBank>)value);
      }
      else if (value != null)
      {
         this.add((JHBank) value);
      }
      
      return this;
   }
   
   public JHBankSet without(JHBank value)
   {
      this.remove(value);
      return this;
   }

   public StringList getSide()
   {
      StringList result = new StringList();
      
      for (JHBank obj : this)
      {
         result.add(obj.getSide());
      }
      
      return result;
   }

   public JHBankSet hasSide(String value)
   {
      JHBankSet result = new JHBankSet();
      
      for (JHBank obj : this)
      {
         if (value.equals(obj.getSide()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }

   public JHBankSet hasSide(String lower, String upper)
   {
      JHBankSet result = new JHBankSet();
      
      for (JHBank obj : this)
      {
         if (lower.compareTo(obj.getSide()) <= 0 && obj.getSide().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }

   public JHBankSet withSide(String value)
   {
      for (JHBank obj : this)
      {
         obj.setSide(value);
      }
      
      return this;
   }

   public JHBoatSet get_rev_on()
   {
      JHBoatSet result = new JHBoatSet();
      
      for (JHBank obj : this)
      {
         result.add(obj.get_rev_on());
      }
      
      return result;
   }

   public JHBankSet has_rev_on(Object value)
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
      
      JHBankSet answer = new JHBankSet();
      
      for (JHBank obj : this)
      {
         if (neighbors.contains(obj.get_rev_on()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   public JHBankSet with_rev_on(JHBoat value)
   {
      for (JHBank obj : this)
      {
         obj.with_rev_on(value);
      }
      
      return this;
   }

   public JHPersonSet get_rev_at()
   {
      JHPersonSet result = new JHPersonSet();
      
      for (JHBank obj : this)
      {
         result.addAll(obj.get_rev_at());
      }
      
      return result;
   }

   public JHBankSet has_rev_at(Object value)
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
      
      JHBankSet answer = new JHBankSet();
      
      for (JHBank obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.get_rev_at()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   public JHBankSet with_rev_at(JHPerson value)
   {
      for (JHBank obj : this)
      {
         obj.with_rev_at(value);
      }
      
      return this;
   }

   public JHBankSet without_rev_at(JHPerson value)
   {
      for (JHBank obj : this)
      {
         obj.without_rev_at(value);
      }
      
      return this;
   }

}
