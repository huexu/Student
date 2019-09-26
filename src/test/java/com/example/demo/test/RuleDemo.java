package com.example.demo.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Verifier;

public class RuleDemo {
	private List<String> systemErrorMessages = new ArrayList<String>();
	@Rule
	public Verifier verifier = new Verifier() {
		@Override
		public void verify() {
			// System.out.println(systemErrorMessages.size());
			assertThat(systemErrorMessages.size(), is(1));
		}
	};

	@Test
	public void testSomething() {
		systemErrorMessages.add("Oh, no!");
	}

	@Test
	public void testSomething1() {
		systemErrorMessages.add("Oh, ");

	}
}