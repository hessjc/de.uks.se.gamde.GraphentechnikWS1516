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
import de.uks.se.learned.Bank;
import java.util.Collection;
import org.sdmlib.models.modelsets.StringList;
import org.sdmlib.models.modelsets.ObjectSet;
import de.uks.se.learned.util.BoatSet;
import de.uks.se.learned.Boat;
import java.util.Collections;
import de.uks.se.learned.util.PersonSet;
import de.uks.se.learned.Person;

public class BankSet extends SDMSet<Bank>
{

   public static final BankSet EMPTY_SET = new BankSet().withReadOnly(true);


   public BankPO hasBankPO()
   {
      return new BankPO(this.toArray(new Bank[this.size()]));
   }


   public String getEntryType()
   {
      return "de.uks.se.learned.Bank";
   }


   @SuppressWarnings("unchecked")
   public BankSet with(Object value)
   {
      if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<Bank>)value);
      }
      else if (value != null)
      {
         this.add((Bank) value);
      }
      
      return this;
   }
   
   public BankSet without(Bank value)
   {
      this.remove(value);
      return this;
   }

   public StringList getSide()
   {
      StringList result = new StringList();
      
      for (Bank obj : this)
      {
         result.add(obj.getSide());
      }
      
      return result;
   }

   public BankSet hasSide(String value)
   {
      BankSet result = new BankSet();
      
      for (Bank obj : this)
      {
         if (value.equals(obj.getSide()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }

   public BankSet hasSide(String lower, String upper)
   {
      BankSet result = new BankSet();
      
      for (Bank obj : this)
      {
         if (lower.compareTo(obj.getSide()) <= 0 && obj.getSide().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }

   public BankSet withSide(String value)
   {
      for (Bank obj : this)
      {
         obj.setSide(value);
      }
      
      return this;
   }

   public BoatSet get_rev_on()
   {
      BoatSet result = new BoatSet();
      
      for (Bank obj : this)
      {
         result.add(obj.get_rev_on());
      }
      
      return result;
   }

   public BankSet has_rev_on(Object value)
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
      
      BankSet answer = new BankSet();
      
      for (Bank obj : this)
      {
         if (neighbors.contains(obj.get_rev_on()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   public BankSet with_rev_on(Boat value)
   {
      for (Bank obj : this)
      {
         obj.with_rev_on(value);
      }
      
      return this;
   }

   public PersonSet get_rev_at()
   {
      PersonSet result = new PersonSet();
      
      for (Bank obj : this)
      {
         result.addAll(obj.get_rev_at());
      }
      
      return result;
   }

   public BankSet has_rev_at(Object value)
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
      
      BankSet answer = new BankSet();
      
      for (Bank obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.get_rev_at()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   public BankSet with_rev_at(Person value)
   {
      for (Bank obj : this)
      {
         obj.with_rev_at(value);
      }
      
      return this;
   }

   public BankSet without_rev_at(Person value)
   {
      for (Bank obj : this)
      {
         obj.without_rev_at(value);
      }
      
      return this;
   }

}
