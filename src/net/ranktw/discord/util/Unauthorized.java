package net.ranktw.discord.util;

public class Unauthorized extends Exception {

	public Unauthorized() {
		super("401: Unauthorized");
	}

	public Unauthorized(String message) {
		super(message);
	}
}