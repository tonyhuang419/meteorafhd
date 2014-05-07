# -*- coding: utf-8 -*-

from bson.code import Code
mapper = Code("""
              function () {
                 this.tags.forEach(function(z) {
                   emit(z, 1);
                });
               }
               """)