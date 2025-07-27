import math
from typing import Tuple, List

def find_primes(start: int, end: int) -> Tuple[int, List[int]]:
    """
    Find all prime numbers in the given interval [start, end] (both endpoints included).
    
    Args:
        start (int): Starting value of the interval
        end (int): Ending value of the interval
    
    Returns:
        Tuple[int, List[int]]: A tuple containing:
            - n: The number of prime numbers found
            - res: List of prime numbers in the interval
    
    Examples:
        >>> find_primes(2, 4)
        (2, [2, 3])
        
        >>> find_primes(2, 12)
        (5, [2, 3, 5, 7, 11])
    """
    def is_prime(num: int) -> bool:
        """Check if a number is prime using optimized trial division."""
        if num < 2:
            return False
        if num == 2:
            return True
        if num % 2 == 0:
            return False
        
        # Check odd divisors up to sqrt(num)
        for i in range(3, int(math.sqrt(num)) + 1, 2):
            if num % i == 0:
                return False
        return True
    
    # Ensure valid input range
    start = max(2, start)  # Primes start from 2
    
    if start > end:
        return 0, []
    
    primes = []
    
    # Handle 2 separately (only even prime)
    if start <= 2 <= end:
        primes.append(2)
    
    # Check odd numbers starting from max(3, start)
    start_odd = max(3, start)
    if start_odd % 2 == 0:
        start_odd += 1
    
    for num in range(start_odd, end + 1, 2):
        if is_prime(num):
            primes.append(num)
    
    return len(primes), primes


def find_primes_sieve(start: int, end: int) -> Tuple[int, List[int]]:
    """
    Alternative implementation using Sieve of Eratosthenes for larger ranges.
    More efficient when finding many primes in a large range.
    
    Args:
        start (int): Starting value of the interval
        end (int): Ending value of the interval
    
    Returns:
        Tuple[int, List[int]]: A tuple containing:
            - n: The number of prime numbers found
            - res: List of prime numbers in the interval
    """
    if start > end or end < 2:
        return 0, []
    
    start = max(2, start)
    
    # Create boolean array for sieve
    is_prime = [True] * (end + 1)
    is_prime[0] = is_prime[1] = False
    
    # Sieve of Eratosthenes
    for i in range(2, int(math.sqrt(end)) + 1):
        if is_prime[i]:
            for j in range(i * i, end + 1, i):
                is_prime[j] = False
    
    # Collect primes in the range
    primes = [i for i in range(start, end + 1) if is_prime[i]]
    
    return len(primes), primes


def benchmark_prime_functions(start: int, end: int) -> None:
    """
    Benchmark both prime-finding implementations and display results.
    """
    import time
    
    print(f"Finding primes in range [{start}, {end}]")
    print("-" * 50)
    
    # Method 1: Trial division
    start_time = time.time()
    n1, primes1 = find_primes(start, end)
    time1 = time.time() - start_time
    
    # Method 2: Sieve of Eratosthenes
    start_time = time.time()
    n2, primes2 = find_primes_sieve(start, end)
    time2 = time.time() - start_time
    
    print(f"Trial Division Method:")
    print(f"  Found {n1} primes in {time1:.6f} seconds")
    print(f"  Primes: {primes1[:20]}{'...' if len(primes1) > 20 else ''}")
    
    print(f"\nSieve of Eratosthenes Method:")
    print(f"  Found {n2} primes in {time2:.6f} seconds")
    print(f"  Primes: {primes2[:20]}{'...' if len(primes2) > 20 else ''}")
    
    print(f"\nResults match: {primes1 == primes2}")
    print(f"Sieve is {time1/time2:.2f}x faster" if time2 < time1 else f"Trial division is {time2/time1:.2f}x faster")


# Test the functions with the provided examples
if __name__ == "__main__":
    print("=== Prime Number Finder Tool ===\n")
    
    # Example 1
    print("Example 1:")
    n, res = find_primes(2, 4)
    print(f"Input: start = 2, end = 4")
    print(f"Output: n = {n}, res = {' '.join(map(str, res))}")
    print()
    
    # Example 2
    print("Example 2:")
    n, res = find_primes(2, 12)
    print(f"Input: start = 2, end = 12")
    print(f"Output: n = {n}, res = {' '.join(map(str, res))}")
    print()
    
    # Additional test cases
    print("Additional Examples:")
    
    # Test case 3: Larger range
    n, res = find_primes(10, 30)
    print(f"Range [10, 30]: n = {n}, primes = {res}")
    
    # Test case 4: Single number ranges
    n, res = find_primes(17, 17)
    print(f"Range [17, 17]: n = {n}, primes = {res}")
    
    n, res = find_primes(20, 20)
    print(f"Range [20, 20]: n = {n}, primes = {res}")
    
    print("\n" + "="*60)
    
    # Benchmark for larger ranges
    print("\nPerformance Comparison:")
    benchmark_prime_functions(1, 1000)