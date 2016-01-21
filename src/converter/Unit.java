/*
 * Copyright (c) 2016 Edilbert Fernando. All rights reserved.
 * Copyright (c) 1995, 2013 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This file is licensed under the 3-Clause BSD/New BSD license.
 *
 * The terms of the license can be found in the "License.txt" file provided.
 */

package converter;

public class Unit {
    String description;
    double multiplier;

    Unit(String description, double multiplier) {
        super();
        this.description = description;
        this.multiplier = multiplier;
    }

    @Override
    public String toString() {
        String s = "Meters/" + description + " = " + multiplier;
        return s;
    }
}