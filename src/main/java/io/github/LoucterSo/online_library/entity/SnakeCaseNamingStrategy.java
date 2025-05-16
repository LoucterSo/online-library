package io.github.LoucterSo.online_library.entity;

import org.hibernate.boot.model.naming.*;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.springframework.stereotype.Component;

import static org.hibernate.internal.util.StringHelper.isNotEmpty;
import static org.hibernate.internal.util.StringHelper.unqualify;

@Component
public class SnakeCaseNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    private String camelCaseToSnakeCase(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                if (i > 0) result.append('_');
                result.append(Character.toLowerCase(currentChar));
            } else {
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    @Override
    protected String transformEntityName(EntityNaming entityNaming) {
        if (isNotEmpty(entityNaming.getJpaEntityName())) {
            return camelCaseToSnakeCase(entityNaming.getJpaEntityName());
        }
        else {
            return camelCaseToSnakeCase(unqualify(entityNaming.getEntityName()));
        }
    }


    @Override
    protected String transformAttributePath(AttributePath attributePath) {
        return camelCaseToSnakeCase(super.transformAttributePath(attributePath));
    }
}