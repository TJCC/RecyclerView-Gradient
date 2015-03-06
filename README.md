# RecyclerView-Gradient
Having a tough time finding a simple way to create a gradient line under the items in a RecyclerView I created one. Modified from recyclerview-playground by devunwired.

Useage:
```
mRecyclerView.addItemDecoration(new DividerDecoration(getActivity(),
                DividerDecoration.DividerType.GRADIENT,
                R.color.navigation_list_divider_color));
```

Note: a lot can be improved this is just a simple implementation sufficient for my needs. There are a lof of changes that could be done to make it more robust and flexible. 

I hope it might be helpful to someone.
