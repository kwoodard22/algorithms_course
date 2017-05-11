# Create members & timestamps

class Generator
  def self.timestamps(members_count, timestamps_count)
    members = (1..members_count).to_a

    timestamps = {}
    (1..timestamps_count).to_a.each do |num|
      timestamps[num] = {pair: [members.sample, members.sample] }
    end
    timestamps
  end
end
