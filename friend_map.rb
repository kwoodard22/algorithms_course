
# Social network connectivity. Given a social network containing n members and a log file
# containing m timestamps at which times pairs of members formed friendships, design an 
# algorithm to determine the earliest time at which all members are connected (i.e., every
# member is a friend of a friend of a friend ... of a friend). Assume that the log file is
# sorted by timestamp and that friendship is an equivalence relation. The running time of 
# your algorithm should be mlogn or better and use extra space proportional to n.

# THOUGHT PROCESS:
# Loop through each timestamp
# Check if pairs are connected through root
  # YES -> Continue
  # NO -> Connect them, decrease unique root count, & continue
  # Keep track of different roots until 1 (root_count)

# What are we checking for?
# Want each member to have the same root (meaning connected)

# TODO: Have random generator prevent duplicate friendships or friendship with self.

# require 'pry'
require_relative 'generator'

class FriendMap
  attr_accessor :members, :timestamps, :id, :size

  def initialize(members_count, timestamps)
    @members = members_count
    @timestamps = timestamps
    @root_count = members_count
  end

  def run
    setup_id_and_size_objects

    timestamps.each do |key, value|
      p, q = value[:pair]
      unless connected?(p, q)
        connect_friends(p, q) 
        @root_count -= 1
      end
      if @root_count == 1
        # puts "All connected at timestamp: #{key}"
        return key
      end
    end
  end

  private

  def setup_id_and_size_objects
    @id = {}
    @size = {}
    (1..members).to_a.each do |i|
      id[i] = i 
      size[i] = 1
    end
  end

  def root(p)
    i = p
    while id[i] != i do
      i = id[i]
    end
    i
  end

  def connect_friends(p, q)
    if size[p] < size[q]
      id[q] = p
      size[p] += size[q]
    else
      id[p] = q
      size[q] += size[p]
    end
  end

  def connected?(p, q)
    root(p) == root(q)
  end
end

timestamps = Generator.timestamps(50, 60)
FriendMap.new(50, timestamps).run