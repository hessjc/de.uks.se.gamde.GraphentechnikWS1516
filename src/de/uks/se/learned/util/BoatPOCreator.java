package de.uks.se.learned.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.json.JsonIdMap;
import de.uks.se.learned.Boat;

public class BoatPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new BoatPO(new Boat[]{});
      } else {
          return new BoatPO();
      }
   }
   
   public static JsonIdMap createIdMap(String sessionID) {
      return de.uks.se.learned.util.CreatorCreator.createIdMap(sessionID);
   }
}
