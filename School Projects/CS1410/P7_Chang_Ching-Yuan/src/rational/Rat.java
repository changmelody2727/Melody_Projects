package rational;

import java.math.BigInteger;

/**
 * Provides rational number (fraction) objects.
 */
public class Rat
{
    /**
     * The numerator. At all times, gcd(num, den) = 1
     */
    private BigInteger num;

    /**
     * The denominator. At all times, den > 0
     */
    private BigInteger den;

    /**
     * Creates the rational number 0
     */
    public Rat ()
    {
        this(0);
    }

    /**
     * Creates the rational number n
     */
    public Rat (long n)
    {
        
        this(n, 1);
        
    }

    /**
     * If d is zero, throws an IllegalArgumentException. Otherwise creates the rational number n/d
     */
    public Rat (long n, long d)
    {
        this(BigInteger.valueOf(n), BigInteger.valueOf(d));
        
       

    }

    /**
     * If d is zero, throws an IllegalArgumentException. Otherwise creates the rational number n/d
     */
    public Rat (BigInteger n, BigInteger d)
    {
        if (d == BigInteger.ZERO)
        {
            throw new IllegalArgumentException();
        }

        if (d.signum() == -1)
        {
            d = d.negate();
            n = n.negate();
        }

        BigInteger g = d.gcd(n.abs());
        n = n.divide(g);
        d = d.divide(g);

        num = n;
        den = d;

    }

    /**
     * Returns the sum of this and r Rat x = new Rat(5, 3); Rat y = new Rat(1, 5); Rat z = x.add(y); a/b + c/d = (ad +
     * bc) / bd
     */
    public Rat add (Rat r)
    {
        BigInteger newNum = this.num.multiply(r.den).add(this.den.multiply(r.num));
        BigInteger newDen = this.den.multiply(r.den);
        return new Rat(newNum, newDen);
    }

    /**
     * Returns the difference of this and r a/b - c/d = (ad - bc) / bd
     */
    public Rat sub (Rat r)
    {
        BigInteger newNum = this.num.multiply(r.den).subtract(this.den.multiply(r.num));
        BigInteger newDen = this.den.multiply(r.den);
        return new Rat(newNum, newDen);
    }

    /**
     * Returns the product of this and r Rat x = new Rat(5, 3); Rat y = new Rat(1, 5); Rat z = x.mul(y); a/b * c/d =
     * ac/bd
     */
    public Rat mul (Rat r)
    {
        BigInteger newNum = this.num.multiply(r.num);
        BigInteger newDen = this.den.multiply(r.den);
        return new Rat(newNum, newDen);
    }

    /**
     * If r is zero, throws an IllegalArgumentException. Otherwise, returns the quotient of this and r. a/b / c/d = ad /
     * bc
     */
    public Rat div (Rat r)
    {
        if (r.toString().equals("0"))
        {
            throw new IllegalArgumentException();
        }
        
        BigInteger newNum = this.num.multiply(r.den);
        BigInteger newDen = this.den.multiply(r.num);
        return new Rat(newNum, newDen);
    }

    /**
     * Returns a negative number if this < r, zero if this = r, a positive number if this > r To compare a/b and c/d,
     * compare ad and bc
     */
    public int compareTo (Rat r)
    {
        BigInteger left = this.num.multiply(r.den);
        BigInteger right = this.den.multiply(r.num);
        if (left.compareTo(right) == -1)
        {
            return -1;
        }
        else if (left.compareTo(right) == 1)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Returns a string version of this in simplest and lowest terms. Examples: 3/4 => "3/4" 6/8 => "3/4" 2/1 => "2" 0/8
     * => "0" 3/-4 => "-3/4"
     */
    public String toString ()
    {
        if (den.toString().equals("1"))
        {
            return num + "";
        }
        else
        {
            return num + "/" + den;
        }
    }

}
