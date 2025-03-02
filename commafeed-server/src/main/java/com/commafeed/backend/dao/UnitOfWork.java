package com.commafeed.backend.dao;

import jakarta.inject.Singleton;

import io.quarkus.narayana.jta.QuarkusTransaction;

@Singleton
public class UnitOfWork {

	public void run(SessionRunner runner) {
		call(() -> {
			runner.runInSession();
			return null;
		});
	}

	public <T> T call(SessionRunnerReturningValue<T> runner) {
		return QuarkusTransaction.joiningExisting().call(runner::runInSession);
	}

	@FunctionalInterface
	public interface SessionRunner {
		void runInSession();
	}

	@FunctionalInterface
	public interface SessionRunnerReturningValue<T> {
		T runInSession();
	}

}
