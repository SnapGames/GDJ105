/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ105
 * 
 * @year 2017
 */
package com.snapgames.gdj.core.state.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.snapgames.gdj.core.state.AbstractGameState;

import net.minidev.json.JSONObject;

/**
 * 
 * <p>
 * GameStateFactory
 * <p>
 * this class is the Window structure reader. it loads all states and there
 * configuration and also the default one.
 * 
 * @author Frédéric Delorme
 *
 */
public class GameStateFactory {

	private static final Logger logger = LoggerFactory.getLogger(GameStateFactory.class);

	private static GameStateFactory instance;

	/**
	 * Internal class StateDefinition a story object represent an entry in the
	 * story.xml file.
	 * 
	 * @author Frédéric Delorme
	 *
	 */
	public class StateDefinition {

		public String name;
		public String className;
		public Class<? extends AbstractGameState> classState;
		public String attributes;
		public JSONObject attrJSON;
		public boolean defaultFlag;

		/**
		 * Create a new StateDefinition.
		 * 
		 * @param storyName
		 * @param storyClass
		 * @param classState
		 * @param attributesState
		 * @param defaultStateFlag
		 */
		@SuppressWarnings("unchecked")
		public StateDefinition(String storyName, String storyClassName, Class<?> classState, String attributesState,
				boolean defaultStateFlag) {
			this.name = storyName;
			this.className = storyClassName;
			this.classState = (Class<? extends AbstractGameState>) classState;
			this.attributes = attributesState;
			this.defaultFlag = defaultStateFlag;
		}

	}

	private GameStateFactory() {

	}

	/**
	 * Here is where the default level is loaded.
	 */
	private String defaultState = "";

	private Map<String, StateDefinition> listStates = null;

	/**
	 * Read the XML file respecting the story.dtd XML definition sheet.
	 * 
	 * @param storyXmlInputStream
	 *            name of the XML file to be read.
	 * @return a Map containing a set of (Name,Class<?>) for all game states.
	 * @see GameStateFactory#load(String storyXmlFilePath)
	 */
	public Map<String, StateDefinition> load(InputStream storyXmlInputStream) {

		return readStoryFromXml(storyXmlInputStream);

	}

	/**
	 * Read the default file story.xml.
	 * 
	 * @see GameStateFactory#load(String storyXmlFilePath)
	 * @return
	 */
	public Map<String, StateDefinition> load() {
		InputStream is = GameStateFactory.class.getResourceAsStream("/res/game.xml");
		return readStoryFromXml(is);
	}

	/**
	 * @param storyXmlInputStream
	 * @return
	 */
	private Map<String, StateDefinition> readStoryFromXml(InputStream storyXmlInputStream) {

		DocumentBuilder db = null;
		String stateClass, stateName, attributesState;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// set a specific resolver to real DTD.
			db = initializeFactory(db);
			Document doc = db.parse(storyXmlInputStream);

			NodeList defaultName = doc.getElementsByTagName("default");
			Node defaultNameItem = defaultName.item(0);
			NamedNodeMap defaultAttMap = defaultNameItem.getAttributes();
			defaultState = defaultAttMap.getNamedItem("name").getNodeValue();

			NodeList list = doc.getElementsByTagName("state");
			listStates = new HashMap<>();
			for (int i = 0; i < list.getLength(); i++) {
				Node item = list.item(i);
				NamedNodeMap attMap = item.getAttributes();

				// retrieve name, class and default value for this state
				stateName = (attMap.getNamedItem("name") != null ? attMap.getNamedItem("name").getNodeValue() : "");
				stateClass = (attMap.getNamedItem("class") != null ? attMap.getNamedItem("class").getNodeValue() : "");
				attributesState = (attMap.getNamedItem("attributes") != null
						? attMap.getNamedItem("attributes").getNodeValue()
						: "");

				Class<?> classState = Class.forName(stateClass);

				StateDefinition story = new StateDefinition(stateName, stateClass, classState, attributesState,
						stateName.equals(defaultState));
				logger.info("add State {} as class {} to the factory", story.name, story.className);
				listStates.put(stateName, story);

			}
		} catch (SAXException | IOException | ClassNotFoundException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return listStates;
	}

	/**
	 * @param db
	 */
	private DocumentBuilder initializeFactory(DocumentBuilder db) {
		db.setEntityResolver(new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				if (systemId.contains("states.dtd")) {
					return new InputSource(GameStateFactory.class.getResourceAsStream("/dtd/states.dtd"));
				} else {
					return null;
				}
			}
		});
		return db;
	}

	/**
	 * Return the default declared StateDefinition (GameState) into the
	 * <code>story.xml</code> file.
	 * 
	 * @return
	 * @throws NoDefaultStateException
	 */
	public StateDefinition getDefault() throws NoDefaultStateException {
		return getStateDefinition(defaultState);
	}

	/**
	 * return the default State
	 * 
	 * @return
	 * @throws NoDefaultStateException
	 */
	public StateDefinition getStateDefinition(String stateName) throws NoDefaultStateException {
		if (stateName != null && !stateName.equals("") && listStates != null && listStates.containsKey(stateName)) {
			return listStates.get(stateName);
		} else {
			throw new NoDefaultStateException(String.format("No %s story were set in the story.xml file !",
					stateName == null ? "null" : stateName));
		}
	}

	public static GameStateFactory getInstance() {
		if (instance == null) {
			instance = new GameStateFactory();
		}
		return instance;
	}

}
