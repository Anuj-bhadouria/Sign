package TC.booking.nutz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class DashBoardActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    int profile_menu= 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        bottomNavigation=findViewById(R.id.dashboard_bottom);

        bottomNavigation.add(new MeowBottomNavigation.Model(profile_menu,R.drawable.ic_profile));

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                if (item.getId() == profile_menu) {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.dashboard_relative, new ProfileFragment()).commit();
                    bottomNavigation.show(profile_menu, true);
                } else {
                }
            }
        });

    }
}