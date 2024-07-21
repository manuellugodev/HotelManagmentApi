package com.manuellugodev.hotel.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;

public class IdGenerator implements IdentifierGenerator {

    private static final Random RANDOM = new SecureRandom();
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        // Use UUID or SecureRandom to generate a random ID
        return RANDOM.nextInt(Integer.MAX_VALUE);    }
}
