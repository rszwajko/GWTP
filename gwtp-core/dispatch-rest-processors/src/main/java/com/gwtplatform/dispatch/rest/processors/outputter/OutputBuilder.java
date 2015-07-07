/*
 * Copyright 2015 ArcBees Inc.
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

package com.gwtplatform.dispatch.rest.processors.outputter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.VelocityContext;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.gwtplatform.dispatch.rest.processors.definitions.HasImports;
import com.gwtplatform.dispatch.rest.processors.definitions.TypeDefinition;

import static com.gwtplatform.dispatch.rest.processors.definitions.HasImports.EXTRACT_IMPORTS_FUNCTION;

public class OutputBuilder {
    private final Outputter outputter;
    private final TypeDefinition processorDefinition;
    private final String templateFile;
    private final VelocityContext context;
    private final Collection<String> imports;

    private Optional<TypeDefinition> typeDefinition;
    private Optional<String> errorLogParameter;

    OutputBuilder(
            Outputter outputter,
            TypeDefinition processorDefinition,
            String templateFile) {
        this.outputter = outputter;
        this.processorDefinition = processorDefinition;
        this.templateFile = templateFile;
        this.context = new VelocityContext();
        this.imports = new HashSet<>();
        this.typeDefinition = Optional.absent();
        this.errorLogParameter = Optional.absent();
    }

    public OutputBuilder withParams(Map<String, Object> params) {
        for (Entry<String, Object> param : params.entrySet()) {
            withParam(param.getKey(), param.getValue());
        }

        return this;
    }

    public OutputBuilder withParam(String key, HasImports value) {
        context.put(key, value);

        return withImports(value.getImports());
    }

    public OutputBuilder withParam(String key, Iterable<? extends HasImports> value) {
        context.put(key, value);

        return withImports(FluentIterable.from(value).transformAndConcat(EXTRACT_IMPORTS_FUNCTION).toList());
    }

    public OutputBuilder withParam(String key, Object value) {
        context.put(key, value);
        return this;
    }

    public OutputBuilder withImports(Collection<String> imports) {
        this.imports.addAll(imports);
        return this;
    }

    public OutputBuilder withImport(String anImport) {
        imports.add(anImport);
        return this;
    }

    public OutputBuilder withErrorLogParameter(String errorLogParameter) {
        this.errorLogParameter = Optional.of(errorLogParameter);
        return this;
    }

    public void writeTo(TypeDefinition typeDefinition) {
        this.typeDefinition = Optional.of(typeDefinition);

        if (!errorLogParameter.isPresent()) {
            errorLogParameter = Optional.of(typeDefinition.getQualifiedName());
        }

        outputter.writeSource(this);
    }

    public String parse() {
        return outputter.parse(this);
    }

    TypeDefinition getProcessorDefinition() {
        return processorDefinition;
    }

    String getTemplateFile() {
        return templateFile;
    }

    VelocityContext getContext() {
        return context;
    }

    Collection<String> getImports() {
        return imports;
    }

    Optional<TypeDefinition> getTypeDefinition() {
        return typeDefinition;
    }

    Optional<String> getErrorLogParameter() {
        return errorLogParameter;
    }
}
