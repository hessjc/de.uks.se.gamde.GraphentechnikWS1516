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
import de.uks.se.learned.JHBoat;
import java.util.Collection;
import org.sdmlib.models.modelsets.ObjectSet;
import de.uks.se.learned.util.JHBankSet;
import de.uks.se.learned.JHBank;

public class JHBoatSet extends SDMSet<JHBoat>
{

   public static final JHBoatSet EMPTY_SET = new JHBoatSet().withReadOnly(true);


   public JHBoatPO hasJHBoatPO()
   {
      return new JHBoatPO(this.toArray(new JHBoat[this.size()]));
   }


   public String getEntryType()
   {
      return "de.uks.se.learned.JHBoat";
   }


   @SuppressWarnings("unchecked")
   public JHBoatSet with(Object value)
   {
      if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<JHBoat>)value);
      }
      else if (value != null)
      {
         this.add((JHBoat) value);
      }
      
      return this;
   }
   
   public JHBoatSet without(JHBoat value)
   {
      this.remove(value);
      return this;
   }

   public JHBankSet getOn()
   {
      JHBankSet result = new JHBankSet();
      
      for (JHBoat obj : this)
      {
         result.add(obj.getOn());
      }
      
      return result;
   }

   public JHBoatSet hasOn(Object value)
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
      
      JHBoatSet answer = new JHBoatSet();
      
      for (JHBoat obj : this)
      {
         if (neighbors.contains(obj.getOn()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   public JHBoatSet withOn(JHBank value)
   {
      for (JHBoat obj : this)
      {
         obj.withOn(value);
      }
      
      return this;
   }

}
