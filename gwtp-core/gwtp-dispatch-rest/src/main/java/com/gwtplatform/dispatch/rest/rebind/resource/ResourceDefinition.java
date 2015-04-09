/**
 * Copyright 2014 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.dispatch.rest.rebind.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.gwtplatform.dispatch.rest.rebind.utils.ClassDefinition;
import com.gwtplatform.dispatch.rest.shared.ContentType;

public class ResourceDefinition extends ClassDefinition {
    private final JClassType resourceInterface;
    private final String path;
    private final boolean secured;
    private final Set<ContentType> consumes;
    private final Set<ContentType> produces;
    private final List<MethodDefinition> methodDefinitions;

    public ResourceDefinition(
            JClassType resourceInterface,
            String packageName,
            String className,
            String path,
            boolean secured,
            Set<ContentType> consumes,
            Set<ContentType> produces) {
        super(packageName, className);

        this.resourceInterface = resourceInterface;
        this.path = path;
        this.secured = secured;
        this.consumes = consumes;
        this.produces = produces;
        this.methodDefinitions = new ArrayList<MethodDefinition>();
    }

    public JClassType getResourceInterface() {
        return resourceInterface;
    }

    public String getPath() {
        return path;
    }

    public boolean isSecured() {
        return secured;
    }

    public Set<ContentType> getConsumes() {
        return consumes;
    }

    public Set<ContentType> getProduces() {
        return produces;
    }

    public void addMethodDefinition(MethodDefinition definition) {
        methodDefinitions.add(definition);
    }

    public List<MethodDefinition> getMethodDefinitions() {
        return new ArrayList<MethodDefinition>(methodDefinitions);
    }
}
