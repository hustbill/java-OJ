// PaintFence 

int paintFence(int n, int k) {
     if(n < 1 || k < 1) return 0;
     else if(n==1) return k;
     
     int same = k;
     int diff = k*(k-1);
     
     for (int i=2; i< n; i++) {
         int old_diff = diff;
         diff = (same + diff) * ( k-1);
         same = old_diff;
         
     }
     
     return  same + diff;
}