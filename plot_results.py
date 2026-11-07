import pandas as pd
import matplotlib.pyplot as plt
import os

os.makedirs("results", exist_ok=True)

data = pd.read_csv("results/results.csv")

# Graph 1: Dynamic Programming

dp = data[data["Category"] == "DP"]

plt.figure(figsize=(9, 5))

for algorithm in ["LCS", "EditDistance"]:

    part = dp[dp["Algorithm"] == algorithm]

    plt.plot(
        part["Size"],
        part["AverageTimeNs"] / 1000000,
        marker="o",
        label=algorithm
    )

plt.xlabel("Input Size")
plt.ylabel("Execution Time (ms)")

plt.title("LCS vs Edit Distance")

plt.legend()
plt.grid(True)

plt.savefig("results/dp_results.png")

plt.close()


# Graph 2: String Matching

matching = data[
    (data["Category"] == "Matching") &
    (data["Case"] == "Absent")
]

plt.figure(figsize=(9, 5))

for algorithm in ["Naive", "KMP", "RabinKarp"]:

    part = matching[matching["Algorithm"] == algorithm]

    plt.plot(
        part["Size"],
        part["AverageTimeNs"] / 1000000,
        marker="o",
        label=algorithm
    )

plt.xlabel("Text Length")
plt.ylabel("Execution Time (ms)")

plt.title("String Matching Comparison")

plt.legend()
plt.grid(True)

plt.savefig("results/matching_results.png")

plt.close()

print("Graphs successfully created!")