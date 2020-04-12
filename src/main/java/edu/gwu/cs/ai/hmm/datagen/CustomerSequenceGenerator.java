package edu.gwu.cs.ai.hmm.datagen;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.gwu.cs.ai.core.Customer;

public class CustomerSequenceGenerator {

	public void generateOneInput(int timeSlots, double[][] transitionProbs, double[][] emissionProbs,
			boolean printStates) throws IOException {
		Customer customer = new Customer();
		addTransitionProbs(customer);
		addEmissionProbs(customer);

		List<String> states = new ArrayList<>();
		List<List<String>> emissions = new ArrayList<>();
		boolean readyToStopCampaign = false;

		states.add(customer.getCurrentStage());
		System.out.println("Starting with " + customer.getCurrentStage());
		for (int i = 0; i < timeSlots; i++) {
			emissions.add(customer.generateOneMove());
			states.add(customer.getCurrentStage());
			if (customer.isSatisfiedCustomer() && readyToStopCampaign) {
				states.remove(states.size() - 1);
				break;
			}
			if (customer.isSatisfiedCustomer()) {
				readyToStopCampaign = true;
			}
		}

		PrintWriter pw = new PrintWriter(
				"src/main/resources/hmm/customer/hmm_customer_" + System.currentTimeMillis() + ".txt");
		pw.println("# Sample input file generated automatically at " + new Date());
		pw.println("# All lines beginning with # are comments and can be ignored");
		pw.println("#");

		// Emissions
		pw.println("# Emissions");
		pw.println("# There is one line per time slot for 40 or so timeslots (interactions)");
		pw.println("# In each timeslot there is a list of links that the user clicked on.  The list may be empty");
		for (List<String> emissionLine : emissions) {
			pw.println(String.join(",", emissionLine));
		}

		if (printStates) {
			// States, for validation only
			pw.println("# States, for testing/validation (Only available in some files)");
			for (String state : states) {
				pw.println(state);
			}
		}

		// Phew, all done
		pw.close();
	}

	private void addEmissionProbs(Customer customer) {
		customer.addEmissionProbability(Customer.STAGE_ZERO, Customer.ACTION_DEMO, 0.1);
		customer.addEmissionProbability(Customer.STAGE_ZERO, Customer.ACTION_VIDEO, 0.01);
		customer.addEmissionProbability(Customer.STAGE_ZERO, Customer.ACTION_TESTIMONIAL, 0.05);
		customer.addEmissionProbability(Customer.STAGE_ZERO, Customer.ACTION_PRICING, 0.3);
		customer.addEmissionProbability(Customer.STAGE_ZERO, Customer.ACTION_BLOG, 0.5);

		customer.addEmissionProbability(Customer.STAGE_AWARE, Customer.ACTION_DEMO, 0.1);
		customer.addEmissionProbability(Customer.STAGE_AWARE, Customer.ACTION_VIDEO, 0.01);
		customer.addEmissionProbability(Customer.STAGE_AWARE, Customer.ACTION_TESTIMONIAL, 0.15);
		customer.addEmissionProbability(Customer.STAGE_AWARE, Customer.ACTION_PRICING, 0.3);
		customer.addEmissionProbability(Customer.STAGE_AWARE, Customer.ACTION_BLOG, 0.4);

		customer.addEmissionProbability(Customer.STAGE_CONSIDERING, Customer.ACTION_DEMO, 0.2);
		customer.addEmissionProbability(Customer.STAGE_CONSIDERING, Customer.ACTION_VIDEO, 0.3);
		customer.addEmissionProbability(Customer.STAGE_CONSIDERING, Customer.ACTION_TESTIMONIAL, 0.05);
		customer.addEmissionProbability(Customer.STAGE_CONSIDERING, Customer.ACTION_PRICING, 0.4);
		customer.addEmissionProbability(Customer.STAGE_CONSIDERING, Customer.ACTION_BLOG, 0.4);

		customer.addEmissionProbability(Customer.STAGE_EXPERIENCING, Customer.ACTION_DEMO, 0.4);
		customer.addEmissionProbability(Customer.STAGE_EXPERIENCING, Customer.ACTION_VIDEO, 0.6);
		customer.addEmissionProbability(Customer.STAGE_EXPERIENCING, Customer.ACTION_TESTIMONIAL, 0.05);
		customer.addEmissionProbability(Customer.STAGE_EXPERIENCING, Customer.ACTION_PRICING, 0.3);
		customer.addEmissionProbability(Customer.STAGE_EXPERIENCING, Customer.ACTION_BLOG, 0.4);

		customer.addEmissionProbability(Customer.STAGE_READY, Customer.ACTION_DEMO, 0.05);
		customer.addEmissionProbability(Customer.STAGE_READY, Customer.ACTION_VIDEO, 0.75);
		customer.addEmissionProbability(Customer.STAGE_READY, Customer.ACTION_TESTIMONIAL, 0.35);
		customer.addEmissionProbability(Customer.STAGE_READY, Customer.ACTION_PRICING, 0.2);
		customer.addEmissionProbability(Customer.STAGE_READY, Customer.ACTION_BLOG, 0.4);

		customer.addEmissionProbability(Customer.STAGE_LOST, Customer.ACTION_DEMO, 0.01);
		customer.addEmissionProbability(Customer.STAGE_LOST, Customer.ACTION_VIDEO, 0.01);
		customer.addEmissionProbability(Customer.STAGE_LOST, Customer.ACTION_TESTIMONIAL, 0.03);
		customer.addEmissionProbability(Customer.STAGE_LOST, Customer.ACTION_PRICING, 0.05);
		customer.addEmissionProbability(Customer.STAGE_LOST, Customer.ACTION_BLOG, 0.2);

		customer.addEmissionProbability(Customer.STAGE_SATISFIED, Customer.ACTION_DEMO, 0.4);
		customer.addEmissionProbability(Customer.STAGE_SATISFIED, Customer.ACTION_VIDEO, 0.4);
		customer.addEmissionProbability(Customer.STAGE_SATISFIED, Customer.ACTION_TESTIMONIAL, 0.01);
		customer.addEmissionProbability(Customer.STAGE_SATISFIED, Customer.ACTION_PRICING, 0.05);
		customer.addEmissionProbability(Customer.STAGE_SATISFIED, Customer.ACTION_BLOG, 0.5);
		customer.addEmissionProbability(Customer.STAGE_SATISFIED, Customer.ACTION_PAYMENT, 1);
	}

	private void addTransitionProbs(Customer customer) {
		customer.addTransitionProbability(Customer.STAGE_ZERO, Customer.STAGE_AWARE, 0.4);

		customer.addTransitionProbability(Customer.STAGE_AWARE, Customer.STAGE_CONSIDERING, 0.3);
		customer.addTransitionProbability(Customer.STAGE_AWARE, Customer.STAGE_READY, 0.01);
		customer.addTransitionProbability(Customer.STAGE_AWARE, Customer.STAGE_LOST, 0.2);

		customer.addTransitionProbability(Customer.STAGE_CONSIDERING, Customer.STAGE_EXPERIENCING, 0.2);
		customer.addTransitionProbability(Customer.STAGE_CONSIDERING, Customer.STAGE_READY, 0.02);
		customer.addTransitionProbability(Customer.STAGE_CONSIDERING, Customer.STAGE_LOST, 0.3);

		customer.addTransitionProbability(Customer.STAGE_EXPERIENCING, Customer.STAGE_READY, 0.3);
		customer.addTransitionProbability(Customer.STAGE_EXPERIENCING, Customer.STAGE_LOST, 0.3);

		customer.addTransitionProbability(Customer.STAGE_READY, Customer.STAGE_SATISFIED, 0.4);
		customer.addTransitionProbability(Customer.STAGE_READY, Customer.STAGE_LOST, 0.2);
	}

	public static void main(String[] args) throws Exception {
		CustomerSequenceGenerator csg = new CustomerSequenceGenerator();
		double[][] emissionProbs = { { 0.6, 0.2, 0.2 }, { 0.2, 0.6, 0.2 }, { 0.2, 0.2, 0.6 } };

		for (int i = 0; i < 100; i++) {
			System.err.println("Generating input number: " + (i + 1));
			csg.generateOneInput(40, emissionProbs, emissionProbs, true);
			Thread.sleep(20);
		}
		for (int i = 0; i < 10; i++) {
			System.err.println("Generating input number: " + (i + 1));
			csg.generateOneInput(40, emissionProbs, emissionProbs, false);
			Thread.sleep(20);
		}
	}
}
