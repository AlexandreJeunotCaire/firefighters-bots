package exceptions;

/**
 * Raised on invalid movement from a robot
 */
public class InvalidMovementException extends Exception
{
    public InvalidMovementException()
    {

    }

    public InvalidMovementException(String message)
    {
        super(message);
    }

}
