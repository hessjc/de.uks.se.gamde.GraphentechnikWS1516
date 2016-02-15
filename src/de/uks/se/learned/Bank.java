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
   
package de.uks.se.learned;

import org.sdmlib.serialization.PropertyChangeInterface;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import org.sdmlib.StrUtil;
import de.uks.se.learned.util.PersonSet;

public  class Bank implements PropertyChangeInterface
{

   
   //==========================================================================
   
   protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
   
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return listeners;
   }
   
   public void addPropertyChangeListener(PropertyChangeListener listener) 
   {
      getPropertyChangeSupport().addPropertyChangeListener(listener);
   }

   
   //==========================================================================
   
   
   public void removeYou()
   {
   
      set_rev_on(null);
      without_rev_at(this.get_rev_at().toArray(new Person[this.get_rev_at().size()]));
      getPropertyChangeSupport().firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_SIDE = "side";
   
   private String side;

   public String getSide()
   {
      return this.side;
   }
   
   public void setSide(String value)
   {
      if ( ! StrUtil.stringEquals(this.side, value)) {
      
         String oldValue = this.side;
         this.side = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_SIDE, oldValue, value);
      }
   }
   
   public Bank withSide(String value)
   {
      setSide(value);
      return this;
   } 


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getSide());
      return result.substring(1);
   }


   
   /********************************************************************
    * <pre>
    *              one                       one
    * Bank ----------------------------------- Boat
    *              on                   _rev_on
    * </pre>
    */
   
   public static final String PROPERTY__REV_ON = "_rev_on";

   private Boat _rev_on = null;

   public Boat get_rev_on()
   {
      return this._rev_on;
   }

   public boolean set_rev_on(Boat value)
   {
      boolean changed = false;
      
      if (this._rev_on != value)
      {
         Boat oldValue = this._rev_on;
         
         if (this._rev_on != null)
         {
            this._rev_on = null;
            oldValue.setOn(null);
         }
         
         this._rev_on = value;
         
         if (value != null)
         {
            value.withOn(this);
         }
         
         getPropertyChangeSupport().firePropertyChange(PROPERTY__REV_ON, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Bank with_rev_on(Boat value)
   {
      set_rev_on(value);
      return this;
   } 

   public Boat create_rev_on()
   {
      Boat value = new Boat();
      with_rev_on(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Bank ----------------------------------- Person
    *              at                   _rev_at
    * </pre>
    */
   
   public static final String PROPERTY__REV_AT = "_rev_at";

   private PersonSet _rev_at = null;
   
   public PersonSet get_rev_at()
   {
      if (this._rev_at == null)
      {
         return PersonSet.EMPTY_SET;
      }
   
      return this._rev_at;
   }

   public Bank with_rev_at(Person... value)
   {
      if(value==null){
         return this;
      }
      for (Person item : value)
      {
         if (item != null)
         {
            if (this._rev_at == null)
            {
               this._rev_at = new PersonSet();
            }
            
            boolean changed = this._rev_at.add (item);

            if (changed)
            {
               item.withAt(this);
               getPropertyChangeSupport().firePropertyChange(PROPERTY__REV_AT, null, item);
            }
         }
      }
      return this;
   } 

   public Bank without_rev_at(Person... value)
   {
      for (Person item : value)
      {
         if ((this._rev_at != null) && (item != null))
         {
            if (this._rev_at.remove(item))
            {
               item.setAt(null);
               getPropertyChangeSupport().firePropertyChange(PROPERTY__REV_AT, item, null);
            }
         }
      }
      return this;
   }

   public Person create_rev_at()
   {
      Person value = new Person();
      with_rev_at(value);
      return value;
   } 
}
