require "minitest/autorun"
require_relative "friend_map"
  
describe FriendMap do
  before do
    member_count = 5
    timestamps = { 
      1=>{ :pair=>[1, 5] },
      2=>{ :pair=>[2, 3] },
      3=>{ :pair=>[4, 3] },
      4=>{ :pair=>[1, 4] }
    }
    @map = FriendMap.new(member_count, timestamps)
  end

  describe "when all members are connected" do
    it "must have the same root" do
      @map.run.must_equal 4
    end
  end
end