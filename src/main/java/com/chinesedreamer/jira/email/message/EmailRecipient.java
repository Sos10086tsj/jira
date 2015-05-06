package com.chinesedreamer.jira.email.message;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class EmailRecipient {
	private String[] to = new String[]{};
	private String[] cc = new String[]{};
	private String[] bcc = new String[]{};
}
