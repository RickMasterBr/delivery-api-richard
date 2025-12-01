
package com.deliverytech.delivery.dto;


public record AppInfo(
    String application,
    String version,
    String developer,
    String javaVersion,
    String framework
) {}