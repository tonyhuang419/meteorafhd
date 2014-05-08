# -*- coding: utf-8 -*-

# http://api.mongodb.org/python/current/examples/aggregation.html
from pymongo import MongoClient
db = MongoClient().things
# db.things.insert({"x": 1, "tags": ["dog", "cat"]})
# db.things.insert({"x": 2, "tags": ["cat"]})
# db.things.insert({"x": 2, "tags": ["mouse", "cat", "dog"]})
# db.things.insert({"x": 3, "tags": []})

from bson.son import SON
print db.things.aggregate([
         {"$unwind": "$tags"},
         {"$group": {"_id": "$tags", "count": {"$sum": 1}}},
         {"$sort": SON([("count", -1), ("_id", -1)])}
     ])

from bson.code import Code
mapper = Code("""
               function () {
                 this.tags.forEach(function(z) {
                   emit(z, 1);
                 });
               }
               """)

reducer = Code("""
                function (key, values) {
                  var total = 0;
                  for (var i = 0; i < values.length; i++) {
                    total += values[i];
                  }
                  return total;
                }
                """)

result = db.things.map_reduce(mapper, reducer, "myresults")
for doc in result.find():
    print doc
    