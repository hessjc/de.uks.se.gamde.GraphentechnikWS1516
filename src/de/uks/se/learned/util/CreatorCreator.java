package de.uks.se.learned.util;

import de.uniks.networkparser.json.JsonIdMap;
import org.sdmlib.serialization.SDMLibJsonIdMap;

class CreatorCreator{

   public static JsonIdMap createIdMap(String sessionID)
   {
      JsonIdMap jsonIdMap = (JsonIdMap) new SDMLibJsonIdMap().withSessionId(sessionID);
      jsonIdMap.withCreator(new BankCreator());
      jsonIdMap.withCreator(new BankPOCreator());
      jsonIdMap.withCreator(new BoatCreator());
      jsonIdMap.withCreator(new BoatPOCreator());
      jsonIdMap.withCreator(new PersonCreator());
      jsonIdMap.withCreator(new PersonPOCreator());
      jsonIdMap.withCreator(new JHBankCreator());
      jsonIdMap.withCreator(new JHBankPOCreator());
      jsonIdMap.withCreator(new JHBoatCreator());
      jsonIdMap.withCreator(new JHBoatPOCreator());
      jsonIdMap.withCreator(new JHPersonCreator());
      jsonIdMap.withCreator(new JHPersonPOCreator());
      return jsonIdMap;
   }
}
