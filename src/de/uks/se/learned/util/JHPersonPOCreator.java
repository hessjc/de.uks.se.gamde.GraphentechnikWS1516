package de.uks.se.learned.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.json.JsonIdMap;
import de.uks.se.learned.JHPerson;

public class JHPersonPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new JHPersonPO(new JHPerson[]{});
      } else {
          return new JHPersonPO();
      }
   }
   
   public static JsonIdMap createIdMap(String sessionID) {
      return de.uks.se.learned.util.CreatorCreator.createIdMap(sessionID);
   }
}
