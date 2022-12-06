/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.services.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
public class UserNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = -6221868913026910626L;

        public UserNotFoundException(String msg){
            super(Problem.DEFAULT_TYPE, msg, Status.NOT_FOUND);
        }
}
