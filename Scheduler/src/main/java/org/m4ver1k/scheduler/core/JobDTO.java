package org.m4ver1k.scheduler.core;

public class JobDTO {
	
	private String name;
	
	private String group;
	
	private String nextFireTime;

	public JobDTO(String name,String group,String fireTime) {
		this.name=name;
		this.group=group;
		this.nextFireTime=fireTime;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
}
