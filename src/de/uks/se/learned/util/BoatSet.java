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
import de.uks.se.learned.Boat;
import java.util.Collection;
import org.sdmlib.models.modelsets.ObjectSet;
import de.uks.se.learned.util.BankSet;
import de.uks.se.learned.Bank;

public class BoatSet extends SDMSet<Boat>
{

   public static final BoatSet EMPTY_SET = new BoatSet().withReadOnly(true);


   public BoatPO hasBoatPO()
   {
      return new BoatPO(this.toArray(new Boat[this.size()]));
   }


   public String getEntryType()
   {
      return "de.uks.se.learned.Boat";
   }


   @SuppressWarnings("unchecked")
   public BoatSet with(Object value)
   {
      if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<Boat>)value);
      }
      else if (value != null)
      {
         this.add((Boat) value);
      }
      
      return this;
   }
   
   public BoatSet without(Boat value)
   {
      this.remove(value);
      return this;
   }

   public BankSet getOn()
   {
      BankSet result = new BankSet();
      
      for (Boat obj : this)
      {
         result.add(obj.getOn());
      }
      
      return result;
   }

   public BoatSet hasOn(Object value)
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
      
      BoatSet answer = new BoatSet();
      
      for (Boat obj : this)
      {
         if (neighbors.contains(obj.getOn()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   public BoatSet withOn(Bank value)
   {
      for (Boat obj : this)
      {
         obj.withOn(value);
      }
      
      return this;
   }

}
