package com.github.attiand.assertj.jaxrs.xml.asserts;

import static com.github.attiand.assertj.jaxrs.xml.asserts.NodeAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class NodeAssertTest {

	@Test
	void shouldExtractNodeFromExpression() throws ParserConfigurationException {
		Node node = createDocument();
		assertThat(node).xpath("test/person").asNode().satisfies(n -> {
			assertThat(n).xpath("name").asString().isEqualTo("John");
		});
	}

	@Test
	void shouldAcceptNodeType() throws ParserConfigurationException {
		Node node = createDocument();
		assertThat(node).xpath("test").asNode().hasType(Node.ELEMENT_NODE);
	}

	@Test
	void shouldAssertNodeType() throws ParserConfigurationException {
		Node node = createDocument();

		var cut = assertThat(node).xpath("test").asNode();

		assertThatThrownBy(() -> cut.hasType(Node.DOCUMENT_NODE)).isInstanceOf(AssertionError.class)
				.hasMessageContaining("Expected node type to be <9> but was <1>");

	}

	@Test
	void shouldAcceptNodeList() throws ParserConfigurationException {
		Node node = createDocument();

		assertThat(node).xpath("test/multipleperson").asNodeList().allSatisfy(n -> {
			assertThat(n).asString().isEqualTo("Bill");
		});
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

		Element otherPerson1 = doc.createElement("multipleperson");
		otherPerson1.setTextContent("Bill");
		root.appendChild(otherPerson1);
		Element otherPerson2 = doc.createElement("multipleperson");
		otherPerson2.setTextContent("Bill");
		root.appendChild(otherPerson2);

		return doc;
	}
}
