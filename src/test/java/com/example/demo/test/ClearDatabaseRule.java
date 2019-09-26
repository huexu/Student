package com.example.demo.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ClearDatabaseRule implements TestRule {
	@Override
	public Statement apply(final Statement base, Description description) {
		return new Statement() {
			// Step 4
			@Override
			public void evaluate() throws Throwable {
				deleteDatabase();
				base.evaluate();
				// code here executes after test is finished
			}

		};

	}

	private void deleteDatabase() {

		// write code to clear the required data in the database.
	}

}
