package de.uks.se.gamde.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.beans.PropertyChangeSupport;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.junit.Test;
import org.sdmlib.models.classes.Card;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.models.classes.Clazz;
import org.sdmlib.models.classes.DataType;
import org.sdmlib.models.classes.Role;
import org.sdmlib.storyboards.Storyboard;

import com.google.gson.JsonArray;

import de.uks.se.gamde.JHGraph;
import de.uks.se.gamde.JHGraphUtil;
import de.uks.se.gamde.JHMatch;
import de.uks.se.gamde.JHNode;
import de.uks.se.gamde.JHNodeSet;
import de.uks.se.gamde.JHOperation;
import de.uks.se.gamde.JHRule;
import de.uks.se.learned.Bank;
import de.uks.se.learned.Boat;
import de.uks.se.learned.Person;

/**
 * Testing-class for missionaries.
 *
 * @author Jan-Christopher Hess (jan-chr.hess@student.uni-kassel.de)
 */
public class testMissionariesProblem
{
   /**
    * 
    * field description.
    */
   private static final String PACKAGE_DE_UKS_SE_LEARNED = "de.uks.se.learned";

   private static final String ATTRIBUTE_TYPE = "type";
   private static final String ATTRIBUTE_SIDE = "side";

   public static final String BANK = "Bank";
   public static final String BOAT = "Boat";
   public static final String PERSON = "Person";
   public static final String CANNIBAL = "cannibal";
   public static final String MISSIONARY = "missionary";

   public static final String IN = "in";
   public static final String ON = "on";
   public static final String AT = "at";

   @SuppressWarnings("unused")
   @Test
   public void testJHGraph() throws Exception
   {
      JHGraph g1 = new JHGraph();

      JHNode leftBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "left");
      JHNode rightBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "right");

      JHNode boat = g1.createNode(BOAT).withEdge(AT, leftBank);

      JHNode m1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);

      JHNode k1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);

      // dump diagram with yuml
      System.out.println(g1.serializeGraph());

      JHGraph startGraph = g1.clone();

      k1.withEdge(IN, boat);
      k2.withEdge(IN, boat);
      k1.withoutEdge(AT, leftBank);
      k2.withoutEdge(AT, leftBank);

      // dump diagram with yuml
      System.out.println(g1.serializeGraph());

      assertEquals("wrong attribute value: ", "left", leftBank.getAttribute(ATTRIBUTE_SIDE));
      assertNotNull("not at bank", m1.getNeighbors(AT));
   }

   @SuppressWarnings("unused")
   @Test
   public void testJHGraphRule() throws Exception
   {
      JHGraph g1 = new JHGraph();

      JHNode leftBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "left");
      JHNode rightBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "right");

      JHNode boat = g1.createNode(BOAT).withEdge(AT, leftBank);

      JHNode m1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);

      JHNode k1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);

      // add graph rewrite rule
      JHRule loadBoatWithOnePerson = new JHRule("loadBoatWithOnePerson");
      JHGraph leftGraph = new JHGraph();

      JHNode personPO = leftGraph.createNode(PERSON);
      JHNode bankPO = leftGraph.createNode(BANK);
      JHNode boatPO = leftGraph.createNode(BOAT);

      personPO.withEdge(AT, bankPO);
      boatPO.withEdge(AT, bankPO);

      loadBoatWithOnePerson.setLeftGraph(leftGraph);

      JHGraph rightGraph = leftGraph.clone();

      JHNode rightPersonPO = rightGraph.getNode(personPO.getId());
      JHNode rightBoatPO = rightGraph.getNode(boatPO.getId());
      JHNode rightBankPO = rightGraph.getNode(bankPO.getId());

      rightPersonPO.withoutEdge(AT, rightBankPO);
      rightPersonPO.withEdge(IN, rightBoatPO);
   }

   @SuppressWarnings("unused")
   @Test
   public void testJHGraphRuleWithBacktracking() throws Exception
   {
      JHGraph g1 = new JHGraph();

      JHNode leftBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "left");
      JHNode rightBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "right");

      JHNode boat = g1.createNode(BOAT).withEdge(AT, leftBank);

      JHNode m1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, rightBank);
      JHNode m2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);

      JHNode k1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);

      // add graph rewrite rule
      JHRule loadBoatWithOnePerson = new JHRule("loadBoatWithOnePerson");
      JHGraph leftGraph = new JHGraph();

      JHNode personPO = leftGraph.createNode(PERSON);
      JHNode bankPO = leftGraph.createNode(BANK);
      JHNode boatPO = leftGraph.createNode(BOAT);

      personPO.withEdge(AT, bankPO);
      boatPO.withEdge(AT, bankPO);

      loadBoatWithOnePerson.setLeftGraph(leftGraph);

      JHGraph rightGraph = leftGraph.clone();

      JHNode rightPersonPO = rightGraph.getNode(personPO.getId());
      JHNode rightBoatPO = rightGraph.getNode(boatPO.getId());
      JHNode rightBankPO = rightGraph.getNode(bankPO.getId());

      rightPersonPO.withoutEdge(AT, rightBankPO);
      rightPersonPO.withEdge(IN, rightBoatPO);
   }

   @SuppressWarnings("unused")
   @Test
   public void testJHGraphSerialization() throws Exception
   {
      JHGraph g1 = new JHGraph();

      JHNode leftBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "left");
      JHNode rightBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "right");

      JHNode boat = g1.createNode(BOAT).withEdge(AT, leftBank);

      JHNode m1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);

      JHNode k1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);

      JHGraphUtil jhGraphUtil = new JHGraphUtil();
      JsonArray json = jhGraphUtil.toJson(g1);
      String originGraph = jhGraphUtil.toString(json);

      JHGraph clone = jhGraphUtil.fromString(originGraph);
      json = jhGraphUtil.toJson(clone);
      String cloneGraph = jhGraphUtil.toString(json);

      assertEquals(originGraph, cloneGraph);
   }

   @SuppressWarnings("unused")
   @Test
   public void testJHGraphRuleReachability() throws Exception
   {
      JHGraph g1 = new JHGraph();

      JHNode leftBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "left");
      JHNode rightBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "right");

      JHNode boat = g1.createNode(BOAT).withEdge(AT, leftBank);

      JHNode m1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);

      JHNode k1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);

      // add graph rewrite rule
      JHRule loadBoatWithOnePerson = new JHRule("loadBoatWithOnePerson");
      JHGraph leftGraph = new JHGraph();

      JHNode personPO = leftGraph.createNode(PERSON);
      JHNode bankPO = leftGraph.createNode(BANK);
      JHNode boatPO = leftGraph.createNode(BOAT);

      personPO.withEdge(AT, bankPO);
      boatPO.withEdge(AT, bankPO);

      loadBoatWithOnePerson.setLeftGraph(leftGraph);

      loadBoatWithOnePerson.addOperation(JHOperation.WITHOUT_EDGE, personPO, AT, bankPO);
      loadBoatWithOnePerson.addOperation(JHOperation.WITH_EDGE, personPO, IN, boatPO);

      LinkedHashSet<JHMatch> matches = loadBoatWithOnePerson.findMatch(g1);

      assertEquals("there should be 6 matches", 6, matches.size());

      // compute reachability graph
      JHGraphUtil jhGraphUtil = new JHGraphUtil();
      jhGraphUtil.setStartGraph(g1);
      jhGraphUtil.addRule(loadBoatWithOnePerson);

      LinkedHashMap<String, JHNodeSet> computeAllReachableGraphs = jhGraphUtil.computeAllReachableGraphs(true);

      System.out.println(computeAllReachableGraphs.size());
   }

   @SuppressWarnings("unused")
   @Test
   public void testJHGraphRuleReachabilityComplete() throws Exception
   {
      JHGraph g1 = new JHGraph();

      JHNode leftBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "left");
      JHNode rightBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "right");

      JHNode boat = g1.createNode(BOAT).withEdge(AT, leftBank);

      JHNode m1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);

      JHNode k1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);

      // add graph rewrite rule
      JHRule loadBoatWithOnePerson = new JHRule("loadBoatWithOnePerson");
      JHGraph leftGraph = new JHGraph();

      JHNode personPO = leftGraph.createNode(PERSON);
      JHNode bankPO = leftGraph.createNode(BANK);
      JHNode boatPO = leftGraph.createNode(BOAT);

      personPO.withEdge(AT, bankPO);
      boatPO.withEdge(AT, bankPO);

      loadBoatWithOnePerson.setLeftGraph(leftGraph);

      loadBoatWithOnePerson.addOperation(JHOperation.WITHOUT_EDGE, personPO, AT, bankPO);
      loadBoatWithOnePerson.addOperation(JHOperation.WITH_EDGE, personPO, IN, boatPO);

      // add graph rewrite rule 2
      JHRule loadBoatWithTwoPerson = new JHRule("loadBoatWithTwoPerson");
      leftGraph = new JHGraph();

      JHNode personPO1 = leftGraph.createNode(PERSON);
      JHNode personPO2 = leftGraph.createNode(PERSON);
      bankPO = leftGraph.createNode(BANK);
      boatPO = leftGraph.createNode(BOAT);

      personPO1.withEdge(AT, bankPO);
      personPO2.withEdge(AT, bankPO);
      boatPO.withEdge(AT, bankPO);

      loadBoatWithTwoPerson.setLeftGraph(leftGraph);

      loadBoatWithTwoPerson.addOperation(JHOperation.WITHOUT_EDGE, personPO1, AT, bankPO);
      loadBoatWithTwoPerson.addOperation(JHOperation.WITHOUT_EDGE, personPO2, AT, bankPO);
      loadBoatWithTwoPerson.addOperation(JHOperation.WITH_EDGE, personPO1, IN, boatPO);
      loadBoatWithTwoPerson.addOperation(JHOperation.WITH_EDGE, personPO2, IN, boatPO);

      // compute reachability graph
      JHGraphUtil jhGraphUtil = new JHGraphUtil();
      jhGraphUtil.setStartGraph(g1);
      jhGraphUtil.addRule(loadBoatWithOnePerson);
      jhGraphUtil.addRule(loadBoatWithTwoPerson);

      LinkedHashMap<String, JHNodeSet> computeAllReachableGraphs = jhGraphUtil.computeAllReachableGraphs(true);
      System.out.println(computeAllReachableGraphs.size());

      JHNodeSet graphSet = g1.getNeighbors("loadBoatWithOnePerson");
      System.out.println("Neighbors (loadBoatWithOnePerson): " + graphSet.size());

      JHNodeSet graphSet2 = g1.getNeighbors("loadBoatWithTwoPerson");
      System.out.println("Neighbors (loadBoatWithTwoPerson): " + graphSet2.size());

      JHNodeSet union = graphSet.union(graphSet2);
      JHNodeSet intersection = graphSet.intersection(graphSet2);
      JHNodeSet minus = graphSet2.minus(graphSet);

      System.out.println("Union: " + union.size());
      System.out.println("Intersection: " + intersection.size());
      System.out.println("Minus: " + minus.size());
   }

   @SuppressWarnings("unused")
   @Test
   public void testClassDiagramDerivation() throws Exception
   {
      JHGraph g1 = new JHGraph();

      JHNode leftBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "left");
      JHNode rightBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "right");

      JHNode boat = g1.createNode(BOAT).withEdge(ON, leftBank);

      JHNode m1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);

      JHNode k1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);

      learnClassModel(g1);
   }

   @SuppressWarnings("unused")
   @Test
   public void testGenericToSpecificGraph() throws Exception
   {
      JHGraph g1 = new JHGraph();

      JHNode leftBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "left");
      JHNode rightBank = g1.createNode(BANK).withAttribute(ATTRIBUTE_SIDE, "right");

      JHNode boat = g1.createNode(BOAT).withEdge(ON, leftBank);

      JHNode m1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);
      JHNode m3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, MISSIONARY).withEdge(AT, leftBank);

      JHNode k1 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k2 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);
      JHNode k3 = g1.createNode(PERSON).withAttribute(ATTRIBUTE_TYPE, CANNIBAL).withEdge(AT, leftBank);

      // generic typed objects
      transformGenericToSpecific(g1);
   }

   @Test
   public void testSpecificToGenericGraph() throws Exception
   {
      ArrayList<Object> g1 = new ArrayList<Object>();

      Bank leftBank = new Bank().withSide("left");
      Bank rightBank = new Bank().withSide("right");
      Boat ship = new Boat().withOn(leftBank);

      Person m1 = new Person().withType(MISSIONARY).withAt(leftBank);
      Person m2 = new Person().withType(MISSIONARY).withAt(leftBank);
      Person m3 = new Person().withType(MISSIONARY).withAt(leftBank);

      Person k1 = new Person().withType(CANNIBAL).withAt(leftBank);
      Person k2 = new Person().withType(CANNIBAL).withAt(leftBank);
      Person k3 = new Person().withType(CANNIBAL).withAt(leftBank);

      g1.addAll(Arrays.asList(leftBank, rightBank, ship, m1, m2, m3, k1, k2, k3));

      // transform to generic
      JHGraph transformSpecificToGenericGraph = transformSpecificToGeneric(g1);
      learnClassModel(transformSpecificToGenericGraph);
      @SuppressWarnings("unused")
      LinkedHashMap<JHNode, Object> transformGenericToSpecific = transformGenericToSpecific(
            transformSpecificToGenericGraph);
   }

   private LinkedHashMap<JHNode, Object> transformGenericToSpecific(JHGraph g1)
   {
      LinkedHashMap<JHNode, Object> result = new LinkedHashMap<JHNode, Object>();

      for (String label : g1.getNodes().keySet())
      {
         for (JHNode jhNode : g1.getNodes().get(label))
         {
            try
            {
               Class<?> javaClass = Class.forName(PACKAGE_DE_UKS_SE_LEARNED + "." + label);

               Object javaObject = javaClass.newInstance();

               result.put(jhNode, javaObject);

               // attributes
               for (String attrName : jhNode.getAttributes().keySet())
               {
                  Object attrValue = jhNode.getAttribute(attrName);
                  Field declaredDField = javaClass.getDeclaredField(attrName);
                  declaredDField.setAccessible(true);
                  declaredDField.set(javaObject, attrValue);
               }
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      }

      // for associations
      for (String label : g1.getNodes().keySet())
      {
         for (JHNode jhNode : g1.getNodes().get(label))
         {
            try
            {
               Class<?> javaClass = Class.forName(PACKAGE_DE_UKS_SE_LEARNED + "." + label);

               Object sourceObject = result.get(jhNode);

               // assocs
               for (String edgeLabel : jhNode.getNeighbors().keySet())
               {
                  if (edgeLabel.startsWith(JHNode._REV))
                  {
                     continue;
                  }

                  JHNodeSet targetNodes = jhNode.getNeighbors(edgeLabel);

                  for (JHNode targetNode : targetNodes)
                  {
                     Object targetObject = result.get(targetNode);

                     Object targetArray = Array.newInstance(targetObject.getClass(), 1);

                     Array.set(targetArray, 0, targetObject);

                     String bla = "withOn";
                     Method declaredMethod = javaClass.getDeclaredMethod(bla, targetArray.getClass());

                     if (declaredMethod != null)
                     {
                        declaredMethod.invoke(sourceObject, targetArray);
                     }
                  }
               }
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      }
      return result;
   }

   private JHGraph transformSpecificToGeneric(ArrayList<Object> g1) throws IllegalAccessException
   {
      LinkedHashMap<Object, JHNode> objectMap = new LinkedHashMap<Object, JHNode>();
      JHGraph g2 = new JHGraph();
      for (Object object : g1)
      {
         String simpleName = object.getClass().getSimpleName();
         JHNode newJHNode = g2.createNode(simpleName);
         objectMap.put(object, newJHNode);
      }
      for (Object object : g1)
      {
         Field[] fields = object.getClass().getDeclaredFields();
         for (Field field : fields)
         {
            if (field.getName().startsWith(JHNode._REV)
                  || field.getType().getName().equals(PropertyChangeSupport.class.getName()))
            {
               continue;
            }
            if (Modifier.isStatic(field.getModifiers()))
            {
               continue;
            }

            field.setAccessible(true);

            if (field.getType().isPrimitive()
                  || field.getType() == String.class)
            {
               Object attrValue = field.get(object);

               JHNode jhNode = objectMap.get(object);
               jhNode.withAttribute(field.getName(), "" + attrValue);
            }
            else if (Collection.class.isAssignableFrom(field.getType()))
            {
               Object attrValue = field.get(object);
               @SuppressWarnings("rawtypes")
               Collection c = (Collection)attrValue;

               if (c != null)
               {
                  JHNode sourceNode = objectMap.get(object);
                  for (Object o : c)
                  {
                     JHNode targetNode = objectMap.get(o);
                     sourceNode.withEdge(field.getName(), targetNode);
                  }
               }
            }
            else // plain model object
            {
               Object attrValue = field.get(object);

               if (attrValue != null)
               {
                  JHNode sourceNode = objectMap.get(object);
                  JHNode targetNode = objectMap.get(attrValue);
                  sourceNode.withEdge(field.getName(), targetNode);
               }
            }
         }
      }

      // change jhNode labels
      JHGraph g3 = new JHGraph();
      for (String label : g2.getNodes().keySet())
      {
         // new label
         String newLabel = "JH" + label;
         for (JHNode n : g2.getNodes().get(label))
         {
            n.setLabel(newLabel);
            g3.witheNodes(n);
         }
      }
      return g3;
   }

   private void learnClassModel(JHGraph g1)
   {
      ClassModel classModel = new ClassModel(PACKAGE_DE_UKS_SE_LEARNED);

      for (JHNodeSet jhNodeSet : g1.getNodes().values())
      {
         for (JHNode jhNode : jhNodeSet)
         {
            String typeValue = jhNode.getLabel();

            String className = PACKAGE_DE_UKS_SE_LEARNED + "." + typeValue.substring(0, 1).toUpperCase()
                  + typeValue.substring(1);
            Clazz clazz = classModel.getClazz(className);

            if (clazz == null)
            {
               clazz = classModel.createClazz(className);
            }

            //learn attributes
            for (String attrName : jhNode.getAttributes().keySet())
            {
               clazz.getOrCreateAttribute(attrName, DataType.STRING);
            }

            // learn assoc
            for (String edgeLabel : jhNode.getNeighbors().keySet())
            {
               if (edgeLabel.startsWith(JHNode._REV))
               {
                  continue;
               }

               // src to target cardinality
               Card targetCard = Card.ONE;
               if (jhNode.getNeighbors(edgeLabel).size() > 1)
               {
                  targetCard = Card.MANY;
               }

               for (JHNode targetNode : jhNode.getNeighbors(edgeLabel))
               {

                  // targetClazz
                  className = PACKAGE_DE_UKS_SE_LEARNED + "." + targetNode.getLabel().substring(0, 1).toUpperCase()
                        + targetNode.getLabel().substring(1);
                  Clazz targetClazz = classModel.getClazz(className);

                  if (targetClazz == null)
                  {
                     targetClazz = classModel.createClazz(className);
                  }

                  Role fwRole = targetClazz.getRoles().hasName(edgeLabel).first();

                  // target to src cardinality (_rev_)
                  Card srcCard = Card.ONE;
                  if (targetNode.getNeighbors(JHNode._REV + edgeLabel).size() > 1)
                  {
                     srcCard = Card.MANY;
                  }

                  if (fwRole == null)
                  {
                     clazz.withAssoc(targetClazz, edgeLabel, targetCard, JHNode._REV + edgeLabel, srcCard);
                     fwRole = clazz.getRoles().hasName(edgeLabel).first();
                  }
                  if (targetCard == Card.MANY)
                  {
                     fwRole.withCard(targetCard.toString());
                  }
               }
            }
         }
      }

      classModel.generate();

      Storyboard storyboard = new Storyboard();
      storyboard.addClassDiagram(classModel);
      storyboard.dumpHTML();
   }
}