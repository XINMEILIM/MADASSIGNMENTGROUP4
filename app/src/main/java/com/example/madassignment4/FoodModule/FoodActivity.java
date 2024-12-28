package com.example.madassignment4.FoodModule;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment4.FoodModule.FoodBean.FoodBean;
import com.example.madassignment4.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FoodActivity extends AppCompatActivity {
    private LinearLayout llWeek, ll_encyclopedia, ll_calculator;
    private LinearLayout ll_main, ll_title;
    private View layout_welcome, layout_welcome1;
    private ImageView iv_back;

    private TextView tv_title;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView rvFoodList;
    private RecyclerView rv_input;
    private RecyclerView rv_calorie_result;
    private WeekdayAdapter adapter;
    private FoodAdapter mFoodAdapter1;
    private FoodAdapter mFoodAdapter2;
    private FoodAdapter mFoodAdapter3;
    private FoodListAdapter mFoodListAdapter;
    private CalorieResultAdapter mCalorieResultAdapter;
    private List<FoodBean> foods0 = new ArrayList<>();
    private List<FoodBean> foods1 = new ArrayList<>();
    private List<FoodBean> foods2 = new ArrayList<>();
    private List<FoodBean> foods3 = new ArrayList<>();
    private View layout_weekly_recipes;
    private View layout_food_list;
    private View layout_food_detail;
    private View layout_input_food;
    private ImageView ivImage;
    private TextView tvDetail;
    private TextView tvNext;
    private TextView tvCalculate;
    private LinearLayout llCalorieInput, llCalorieResult;
    private boolean isMain = true;
    private CalorieAdapter mCalorieAdapter;
    private List<FoodBean> calorieList = new ArrayList<>();
    private List<FoodBean> calorieResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        calorieList.add(new FoodBean("", 0, "", 0, 0));
        mCalorieAdapter = new CalorieAdapter(calorieList);
        mCalorieResultAdapter = new CalorieResultAdapter(calorieResultList);
        foods0.add(new FoodBean("Egg", R.mipmap.icon_egg, "1", R.string.egg, 140));
        foods0.add(new FoodBean("Bread", R.mipmap.icon_bread, "300", R.string.bread, 120));
        foods0.add(new FoodBean("Orange juice", R.mipmap.icon_orange_juice, "500", R.string.orange_juice, 22));
        foods0.add(new FoodBean("Rice", R.mipmap.icon_rice, "150", R.string.rice, 34)); 
        foods0.add(new FoodBean("Shrimp", R.mipmap.icon_shrimp, "100", R.string.shrimp, 56));
        foods0.add(new FoodBean("Stir-fried vegetables", R.mipmap.icon_vegetables, "150", R.string.vegetables, 143));
        foods0.add(new FoodBean("Chicken breast", R.mipmap.icon_chicken, "100", R.string.chicken, 233));
        foods0.add(new FoodBean("Purple sweet potato", R.mipmap.icon_purple_sweet_potato, "200", R.string.sweet_potato, 44));
        foods0.add(new FoodBean("Milk", R.mipmap.icon_milk, "500", R.string.milk, 77)); 

        llWeek = findViewById(R.id.ll_week);
        ll_encyclopedia = findViewById(R.id.ll_encyclopedia);
        ll_calculator = findViewById(R.id.ll_calculator);
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        tvCalculate = findViewById(R.id.tv_calculate);
        iv_back.setOnClickListener(v -> {
            if (!isMain) {
                tv_title.setText("");
                layout_weekly_recipes.setVisibility(View.GONE);
                layout_food_list.setVisibility(View.GONE);
                layout_food_detail.setVisibility(View.GONE);
                layout_weekly_recipes.setVisibility(View.GONE);
                layout_input_food.setVisibility(View.GONE);
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView2 = findViewById(R.id.rv_breakfast);
        recyclerView3 = findViewById(R.id.rv_lunch);
        recyclerView4 = findViewById(R.id.rv_dinner);
        rvFoodList = findViewById(R.id.rv_foods);
        ll_main = findViewById(R.id.ll_main);
        ll_title = findViewById(R.id.ll_title);
        layout_welcome = findViewById(R.id.layout_welcome);
        layout_welcome1 = findViewById(R.id.layout_welcome1);
        tvNext = findViewById(R.id.tv_next);
        tvNext.setOnClickListener(v -> {
            layout_welcome.setVisibility(View.GONE);
            layout_welcome1.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layout_welcome1.setVisibility(View.GONE);
                    ll_main.setVisibility(View.VISIBLE);
                    ll_title.setVisibility(View.VISIBLE);
                }
            }, 2000);
        });

        mFoodAdapter1 = new FoodAdapter(foods1);
        mFoodAdapter2 = new FoodAdapter(foods2);
        mFoodAdapter3 = new FoodAdapter(foods3);
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView4.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView2.setAdapter(mFoodAdapter1);
        recyclerView3.setAdapter(mFoodAdapter2);
        recyclerView4.setAdapter(mFoodAdapter3);
        randomSelectFoods(foods1, 3);
        randomSelectFoods(foods2, 4);
        randomSelectFoods(foods3, 2);
        mFoodAdapter1.notifyDataSetChanged();
        mFoodAdapter2.notifyDataSetChanged();
        mFoodAdapter3.notifyDataSetChanged();

        mFoodListAdapter = new FoodListAdapter(this, foods0);
        mFoodListAdapter.setListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                layout_food_detail.setVisibility(View.VISIBLE);
                layout_food_list.setVisibility(View.GONE);
                FoodBean foodBean = foods0.get(position);
                tv_title.setText(foodBean.getDetail());
                ivImage.setImageResource(foodBean.getImage());
                tvDetail.setText(getResources().getString(foodBean.getDetail()));
                isMain = false;
            }
        });
        rvFoodList.setLayoutManager(new LinearLayoutManager(this));
        rvFoodList.setAdapter(mFoodListAdapter);

        layout_weekly_recipes = findViewById(R.id.layout_weekly_recipes);
        layout_food_list = findViewById(R.id.layout_food_list);
        layout_food_detail = findViewById(R.id.layout_food_detail);
        layout_input_food = findViewById(R.id.layout_input_food);
        ivImage = findViewById(R.id.iv_image);
        tvDetail = findViewById(R.id.tv_detail);

        rv_input = findViewById(R.id.rv_input);
        rv_calorie_result = findViewById(R.id.rv_calorie_result);
        rv_calorie_result.setLayoutManager(new LinearLayoutManager(this));
        rv_calorie_result.setAdapter(mCalorieResultAdapter);
        rv_input.setLayoutManager(new LinearLayoutManager(this));
        mCalorieAdapter.setListener(new CalorieAdapter.OnItemClickListener() {
            @Override
            public void add(int position) {
                calorieList.add(new FoodBean("", 0, "", 0, 0));
                mCalorieAdapter.notifyDataSetChanged();
            }

            @Override
            public void delete(int position) {
                calorieList.remove(position);
                mCalorieAdapter.notifyDataSetChanged();
            }
        });
        mCalorieAdapter.setOnTextChangeListener(new CalorieAdapter.OnTextChangeListener() {
            @Override
            public void changeName(int position, String name) {
                calorieList.get(position).setName(name);
            }

            @Override
            public void changeCount(int position, String count) {
                calorieList.get(position).setCount(count);
            }
        });
        rv_input.setAdapter(mCalorieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<String> weekdays = Arrays.asList("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN");
        adapter = new WeekdayAdapter(weekdays);
        adapter.setListener(new WeekdayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                randomSelectFoods(foods1, 3);
                randomSelectFoods(foods2, 4);
                randomSelectFoods(foods3, 2);
                mFoodAdapter1.notifyDataSetChanged();
                mFoodAdapter2.notifyDataSetChanged();
                mFoodAdapter3.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);

        llWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_food_list.setVisibility(View.GONE);
                layout_weekly_recipes.setVisibility(View.VISIBLE);
                layout_input_food.setVisibility(View.GONE);
                isMain = false;
            }
        });
        ll_encyclopedia.setOnClickListener(v -> {
            tv_title.setText("Food Encyclopedia");
            layout_food_list.setVisibility(View.VISIBLE);
            layout_weekly_recipes.setVisibility(View.GONE);
            layout_input_food.setVisibility(View.GONE);
            isMain = false;
        });
        ll_calculator.setOnClickListener(v -> {
            tv_title.setText("Calorie Calculator");
            layout_food_list.setVisibility(View.GONE);
            layout_weekly_recipes.setVisibility(View.GONE);
            layout_input_food.setVisibility(View.VISIBLE);
            isMain = false;
        });
        llCalorieInput = findViewById(R.id.ll_input);
        llCalorieResult = findViewById(R.id.ll_result);
        tvCalculate.setOnClickListener(v -> {
            calorieResultList.add(new FoodBean());
            tv_title.setText("Calorie Calculator");
            llCalorieInput.setVisibility(View.GONE);
            llCalorieResult.setVisibility(View.VISIBLE);
            int result = 0;
            for (FoodBean foodBean : calorieList) {
                int indexOf = foods0.indexOf(foodBean);
                if (indexOf >= 0) {
                    FoodBean foodBean1 = new FoodBean();
                    foodBean1.setCalorie(foods0.get(indexOf).getCalorie());
                    foodBean1.setName(foodBean.getName());
                    foodBean1.setCount(foodBean.getCount());
                    calorieResultList.add(foodBean1);
                    result += foodBean1.getCalorie() * Integer.parseInt(foodBean1.getCount());
                }
                mCalorieResultAdapter.notifyDataSetChanged();
            }
            ((TextView) findViewById(R.id.tv_calorie_result)).append(result + "");
        });

    }

    private void randomSelectFoods(List<FoodBean> selectedFoods, int count) {
        selectedFoods.clear(); // 清空之前的选择
        Random random = new Random();
        while (selectedFoods.size() < count) {
            int index = random.nextInt(foods0.size());
            FoodBean food = foods0.get(index);
            if (!selectedFoods.contains(food)) {
                selectedFoods.add(food);
            }
        }
    }
}
