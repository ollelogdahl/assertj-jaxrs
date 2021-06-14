package com.github.attiand.assertj.jaxrs.xml.asserts;

import static com.github.attiand.assertj.jaxrs.xml.asserts.NodeAssert.assertThat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class XPathExpressionAssertTest {

	@Test
	void shouldExtractStringFromNode() throws ParserConfigurationException {
		Node node = createDocument();
		assertThat(node).xpath("/test/person/name").asString().isEqualTo("John");
	}

	@Test
	void shouldExtractIntegerFromNode() throws ParserConfigurationException {
		Node node = createDocument();
		assertThat(node).xpath("/test/person/age").asInteger().isEqualTo(20).isGreaterThan(19);
	}

	@Test
	void shouldExtractDoubleFromNode() throws ParserConfigurationException {
		Node node = createDocument();
		assertThat(node).xpath("/test/real").asDouble().isEqualTo(10.3).isBetween(10.2, 10.4);
	}

	@Test
	void shouldExtractBooleanFromNode() throws ParserConfigurationException {
		Node node = createDocument();
		assertThat(node).xpath("boolean(/test/person/name)").asBoolean().isTrue();
		assertThat(node).xpath("not(boolean(/test/person/name))").asBoolean().isFalse();
	}

	private static Document createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element root = doc.createElement("test");
		doc.appendChild(root);

		Element person = doc.createElement("person");
		root.appendChild(person);

		Element name = doc.createElement("name");
		name.setTextContent("John");
		person.appendChild(name);

		Element age = doc.createElement("age");
		age.setTextContent(Integer.toString(20));
		person.appendChild(age);

		Element real = doc.createElement("real");
		real.setTextContent(Double.toString(10.3));
		root.appendChild(real);

		return doc;
	}
}
