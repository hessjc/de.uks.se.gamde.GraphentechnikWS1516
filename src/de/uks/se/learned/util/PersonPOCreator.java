package de.uks.se.learned.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.json.JsonIdMap;
import de.uks.se.learned.Person;

public class PersonPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new PersonPO(new Person[]{});
      } else {
          return new PersonPO();
      }
   }
   
   public static JsonIdMap createIdMap(String sessionID) {
      return de.uks.se.learned.util.CreatorCreator.createIdMap(sessionID);
   }
}
